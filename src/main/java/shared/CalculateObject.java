package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculateObject extends Remote {
    ResultType calculate(Parameter inputParam) throws RemoteException;
}
