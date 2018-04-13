import java.rmi.Naming;

public class Client {

    public static void main(String[] args) {
        waitForServer();
        Commons.setSecurityManager();
        CalcObject object = fetchObject();
        runRemoteCalculate(object);
    }

    private static void waitForServer() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static CalcObject fetchObject() {
        String serverAddress = Commons.ADDRESS;
        CalcObject object;
        try {
            object = (CalcObject) Naming.lookup(serverAddress);
        } catch (Exception e) {
            System.out.println("Nie mozna pobrac referencji do " + serverAddress);
            e.printStackTrace();
            return null;
        }
        System.out.println("Referencja do " + serverAddress + " jest pobrana.");
        return object;
    }

    private static Double runRemoteCalculate(CalcObject object) {
        Double result;
        try {
            result = object.calculate(1.1, 2.2);
        } catch (Exception e) {
            System.out.println("Blad zdalnego wywolania.");
            e.printStackTrace();
            return null;
        }
        System.out.println("Wynik = " + result);
        return result;
    }

}
