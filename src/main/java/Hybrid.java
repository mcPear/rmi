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
        runRemoteInputCalculateObjects(calculateObjects);
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

    private static Double runRemoteInputCalculateObjects(List<CalculateObject> calculateObjects) throws RemoteException {
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
            parameter = new Parameter("", j, j + incrementationJ);
            result += runRemoteInputCalculateObject(object, parameter);
            j += incrementationJ;
        }
        System.out.println("Wynik zbiorczy: " + result);
        return result;
    }

    private static Double runRemoteInputCalculateObject(CalculateObject object, Parameter parameter) {
        ResultType result;
        try {
            result = object.calculate(parameter);
        } catch (Exception e) {
            System.out.println("Blad zdalnego wywolania.");
            e.printStackTrace();
            return null;
        }
        System.out.println("Wynik = " + result.result + ", description: " + result.description);
        return result.result;
    }

    private static boolean isServer(String name) {
        return name.equals("server");
    }

}
