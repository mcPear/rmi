package shared;

import java.io.Serializable;

public class Parameter implements Serializable {
    private static final long serialVersionUID = 101L;
    public String operation;
    public long startJ;
    public long endJ;

    public Parameter(String operation, long startJ, long endJ) {
        this.operation = operation;
        this.startJ = startJ;
        this.endJ = endJ;
    }
}
