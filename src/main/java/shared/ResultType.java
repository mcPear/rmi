package shared;

import java.io.Serializable;

public class ResultType implements Serializable {
    private static final long serialVersionUID = 102L;
    public String description;
    public double result;

    public ResultType(double result) {
        this.result = result;
    }
}
