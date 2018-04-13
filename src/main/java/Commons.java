public class Commons {
    public static final String ADDRESS = "//localhost/rmi_service";

    public static void setSecurityManager() {
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
    }
}
