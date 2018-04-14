package shared;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculateObjectImpl extends UnicastRemoteObject implements CalculateObject {

    public CalculateObjectImpl() throws RemoteException {
        super();
    }

    public ResultType calculate(Parameter inParam) throws RemoteException {
        boolean step = false;
        double result = 0;
        long j = inParam.startJ;
        while (j < inParam.endJ) {
            step = !step;
            if (step) {
                result += 4.0 / (j * (j + 1) * (j + 2));
            } else {
                result -= 4.0 / (j * (j + 1) * (j + 2));
            }
            System.out.println("step: " + step + ", result: " + result+", j: "+j);
            j += 2;
        }
        return new ResultType(result);
    }
}
