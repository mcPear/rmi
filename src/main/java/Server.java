import shared.*;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        createRegistry();
        Commons.setSecurityManager();
        rebindServer();
    }

    private static void rebindServer() {
        try {
            CalcObject object = new CalcObjectImpl();
            Naming.rebind(Commons.ADDRESSES.get(0), object);
            System.out.println("Server is registered now :-)");
            System.out.println("Press Crl+C to stop...");
        } catch (Exception e) {
            System.out.println("SERVER CAN'T BE REGISTERED!");
            e.printStackTrace();
        }

        try {
            CalculateInputObject object = new CalculateInputObjectImpl();
            Naming.rebind(Commons.ADDRESSES.get(1), object);
            System.out.println("Server is registered now :-)");
            System.out.println("Press Crl+C to stop...");
        } catch (Exception e) {
            System.out.println("SERVER CAN'T BE REGISTERED!");
            e.printStackTrace();
        }
    }

    private static void createRegistry() {
        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

}
