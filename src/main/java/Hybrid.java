import shared.*;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Hybrid {

    public static void main(String... args) throws RemoteException {
        System.out.println(args[0]);
        RegistryUtil.createRegistry();
        RegistryUtil.setSecurityManager();
        if (isServer(args[0])) {
            runAsWorker();
        } else {
            runAsFarmer();
        }
    }

    private static void runAsWorker() throws RemoteException {
        String nextAddress = RegistryUtil.getNextUnregisteredAddress();

        try {
            CalculateObject object = new CalculateObjectImpl();
            Naming.rebind(nextAddress, object);
            System.out.println("Worker server is registered now :-) on " + nextAddress);
        } catch (Exception e) {
            System.out.println("SERVER CAN'T BE REGISTERED!");
            e.printStackTrace();
        }
    }

    private static void runAsFarmer() throws RemoteException {
        List<CalculateObject> calculateObjects = fetchCalculateObjects();
        //calculatePiOnRemoteObjects(calculateObjects);
        calculateEulerNumberOnRemoteObjects(calculateObjects);
    }


    private static List<CalculateObject> fetchCalculateObjects() throws RemoteException {
        List<CalculateObject> calculateObjects = new ArrayList<>();
        List<String> registeredAddresses = RegistryUtil.getRegisteredAddresses();
        registeredAddresses.forEach(address -> calculateObjects.add(fetchCalculateObject(address)));
        return calculateObjects;
    }

    private static CalculateObject fetchCalculateObject(String address) {
        CalculateObject object;
        try {
            object = (CalculateObject) Naming.lookup(address);
        } catch (Exception e) {
            System.out.println("Nie mozna pobrac referencji do " + address);
            e.printStackTrace();
            return null;
        }
        System.out.println("Referencja do " + address + " jest pobrana.");
        return object;
    }

    private static Double calculatePiOnRemoteObjects(List<CalculateObject> calculateObjects) throws RemoteException {
        int theBestIterationsCount = 40;
        double result = 3;
//        int iterations = 10;
        int iterations = theBestIterationsCount / RegistryUtil.getServersCount();
        if (iterations % 2 == 1) {
            iterations += 1;
        }
        int incrementationJ = iterations * 2;
        int j = 2;
        Parameter parameter;
        for (CalculateObject object : calculateObjects) {
            parameter = new Parameter("pi", j, j + incrementationJ);
            result += runRemoteInputCalculateObject(object, parameter).result;
            j += incrementationJ;
        }
        System.out.println("Wynik zbiorczy: " + result);
        return result;
    }

    private static Double calculateEulerNumberOnRemoteObjects(List<CalculateObject> calculateObjects) throws RemoteException {
        int theBestIterationsCount = 30;
        double sum = 0;
        int iterations = theBestIterationsCount / RegistryUtil.getServersCount();
        int j = 0;
        double factorial = 1;
        Parameter parameter;
        ResultType result = ResultType.empty();
        for (CalculateObject object : calculateObjects) {
            parameter = new Parameter("euler", j, j + iterations, factorial);
            result = runRemoteInputCalculateObject(object, parameter);
            sum += result.result;
            j += iterations;
            factorial = result.factorial;
            System.out.println(factorial);
        }
        System.out.println("Wynik zbiorczy: " + sum);
        return result.result;
    }

    private static ResultType runRemoteInputCalculateObject(CalculateObject object, Parameter parameter) {
        ResultType result;
        try {
            result = object.calculate(parameter);
        } catch (Exception e) {
            System.out.println("Blad zdalnego wywolania.");
            e.printStackTrace();
            return null;
        }
        System.out.println("Wynik = " + result.result + ", description: " + result.description);
        return result;
    }

    private static boolean isServer(String name) {
        return name.equals("server");
    }

}
