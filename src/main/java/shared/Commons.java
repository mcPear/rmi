package shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commons {
    public static final List<String> ADDRESSES = new ArrayList<>(Arrays.asList(
            "//localhost/rmi_service1",
            "//localhost/rmi_service2"
    ));

    public static void setSecurityManager() {
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
    }
}
