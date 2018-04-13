import shared.*;

import java.rmi.Naming;

public class Client {

    public static void main(String[] args) {
        waitForServer();
        Commons.setSecurityManager();
        CalcObject object = fetchObject();
        CalculateInputObject inputObject = fetchCalculateObject();
        runRemoteCalculate(object);
        runRemoteInputCalculate(inputObject);
    }

    private static void waitForServer() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static CalcObject fetchObject() {
        String serverAddress = Commons.ADDRESSES.get(0);
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


    private static CalculateInputObject fetchCalculateObject() {
        String serverAddress = Commons.ADDRESSES.get(1);
        CalculateInputObject object;
        try {
            object = (CalculateInputObject) Naming.lookup(serverAddress);
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

    private static Double runRemoteInputCalculate(CalculateInputObject object) {
        InputType inputType = new InputType("add", 2.3, 3.05);
        ResultType result;
        try {
            result = object.calculate(inputType);
        } catch (Exception e) {
            System.out.println("Blad zdalnego wywolania.");
            e.printStackTrace();
            return null;
        }
        System.out.println("Wynik = " + result.result + ", description: "+result.description);
        return result.result;
    }

}
