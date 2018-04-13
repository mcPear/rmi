package shared;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegistryUtil {
    public static final List<String> ADDRESSES = new ArrayList<>(Arrays.asList(
            "//localhost/rmi_service1",
            "//localhost/rmi_service2",
            "//localhost/rmi_service3",
            "//localhost/rmi_service4",
            "//localhost/rmi_service5",
            "//localhost/rmi_service6"
    ));
    private static final List<String> SERVICES = ADDRESSES.stream().map(a -> addressToService(a)).collect(Collectors.toList());

    public static void setSecurityManager() {
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
    }

    public static String getNextUnregisteredAddress() throws RemoteException { //FIXME increment, don't predefine
        List<String> registeredServices = Arrays.asList(LocateRegistry.getRegistry("localhost").list());
        List<String> unregisteredServices = new ArrayList<>(SERVICES);
        unregisteredServices.removeAll(registeredServices);
        return unregisteredServices.isEmpty() ? null : serviceToAddress(unregisteredServices.get(0));
    }

    public static List<String> getRegisteredAddresses() throws RemoteException {
        return Arrays.stream(LocateRegistry.getRegistry("localhost").list())
                .map(s -> serviceToAddress(s))
                .collect(Collectors.toList());
    }

    private static String addressToService(String address) {
        return address.replace("//localhost/", "");
    }

    private static String serviceToAddress(String service) {
        return "//localhost/" + service;
    }

    public static void createRegistry() {
        try {
            LocateRegistry.createRegistry(1099);
        } catch (ExportException e) {
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

}
