package shared;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculateInputObjectImpl extends UnicastRemoteObject implements CalculateInputObject {

    public CalculateInputObjectImpl() throws RemoteException {
        super();
    }

    public ResultType calculate(InputType inParam) throws RemoteException {
        double zm1, zm2;
        ResultType wynik = new ResultType();
        zm1 = inParam.x1;
        zm2 = inParam.x2;
        wynik.description = "Operacja " + inParam.operation;
        switch (inParam.operation) {
            case "add":
                wynik.result = zm1 + zm2;
                break;
            case "sub":
                wynik.result = zm1 - zm2;
                break;
            default:
                wynik.result = 0;
                wynik.description = "Podano zla operacje";
                return wynik;
        }
        return wynik;
    }
}
