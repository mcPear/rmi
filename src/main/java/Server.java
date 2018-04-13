import java.rmi.Naming;

public class Server {

    public static void main(String[] args) {
        Commons.setSecurityManager();
        rebindServer();
    }

    private static void rebindServer() {
        try {
            CalcObject object = new CalcObjectImpl();
            Naming.rebind(Commons.ADDRESS, object);
            System.out.println("Server is registered now :-)");
            System.out.println("Press Crl+C to stop...");
        } catch (Exception e) {
            System.out.println("SERVER CAN'T BE REGISTERED!");
            e.printStackTrace();
        }
    }

}
