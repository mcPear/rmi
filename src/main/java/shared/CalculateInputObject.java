package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculateInputObject extends Remote {
    ResultType calculate(InputType inputParam) throws RemoteException;
}
