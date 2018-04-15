package shared;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculateObjectImpl extends UnicastRemoteObject implements CalculateObject {

    public CalculateObjectImpl() throws RemoteException {
        super();
    }

    public ResultType calculate(Parameter inParam) throws RemoteException {
        switch (inParam.operation) {
            case "pi":
                return calculateNilakanthaPi(inParam);
            case "euler":
                return calculateEulerConstant(inParam);
            default:
                throw new IllegalArgumentException("missing method: " + inParam.operation);
        }
    }

    private ResultType calculateNilakanthaPi(Parameter inParam) {
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
            System.out.println("step: " + step + ", result: " + result + ", j: " + j);
            j += 2;
        }
        return new ResultType(result);
    }

    private ResultType calculateEulerConstant(Parameter inParam) {
        System.out.println(inParam.factorial);
        double factorial = inParam.factorial;
        double sum = 0;
        long j = inParam.startJ;
        while (j < inParam.endJ) {
            sum += 1d / factorial;
            factorial *= j + 1;
            j++;
        }
        return new ResultType(sum, factorial);
    }

}
