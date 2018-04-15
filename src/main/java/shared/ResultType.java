package shared;

import java.io.Serializable;

public class ResultType implements Serializable {
    private static final long serialVersionUID = 102L;
    public String description;
    public double result;
    public double factorial;

    public ResultType(double result, double factorial) {
        this.result = result;
        this.factorial = factorial;
    }

    public ResultType(double result) {
        this.result = result;
    }

    private ResultType() {
    }

    public static ResultType empty() {
        return new ResultType();
    }
}
