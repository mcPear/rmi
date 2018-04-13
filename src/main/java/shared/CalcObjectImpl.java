package shared;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalcObjectImpl extends UnicastRemoteObject implements CalcObject {
    private static final long serialVersionUID = 101L;

    public CalcObjectImpl() throws RemoteException {
        super();
    }

    public double calculate(double a, double b) throws RemoteException {
        return a + b;
    }
}