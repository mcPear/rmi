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
            CalculateInputObject object = new CalculateInputObjectImpl();
            Naming.rebind(nextAddress, object);
            System.out.println("Worker server is registered now :-) on " + nextAddress);
        } catch (Exception e) {
            System.out.println("SERVER CAN'T BE REGISTERED!");
            e.printStackTrace();
        }
    }

    private static void runAsFarmer() throws RemoteException {
        List<CalculateInputObject> calculateObjects = fetchCalculateObjects();
        runRemoteInputCalculateObjects(calculateObjects);
    }


    private static List<CalculateInputObject> fetchCalculateObjects() throws RemoteException {
        List<CalculateInputObject> calculateObjects = new ArrayList<>();
        List<String> registeredAddresses = RegistryUtil.getRegisteredAddresses();
        registeredAddresses.forEach(address -> calculateObjects.add(fetchCalculateObject(address)));
        return calculateObjects;
    }

    private static CalculateInputObject fetchCalculateObject(String address) {
        CalculateInputObject object;
        try {
            object = (CalculateInputObject) Naming.lookup(address);
        } catch (Exception e) {
            System.out.println("Nie mozna pobrac referencji do " + address);
            e.printStackTrace();
            return null;
        }
        System.out.println("Referencja do " + address + " jest pobrana.");
        return object;
    }

    private static Double runRemoteInputCalculateObjects(List<CalculateInputObject> calculateObjects) {
        double result = 0;
        for (CalculateInputObject object : calculateObjects) {
            result += runRemoteInputCalculateObject(object);
        }
        System.out.println("Wynik zbiorczy: "+result);
        return result;
    }

    private static Double runRemoteInputCalculateObject(CalculateInputObject object) {
        InputType inputType = new InputType("add", 2.3, 3.05);
        ResultType result;
        try {
            result = object.calculate(inputType);
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