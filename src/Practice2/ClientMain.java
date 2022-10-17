package Practice2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ClientMain {
    public static final String NAME = "server.equation";
    public static void main(String[] args) throws RemoteException, NotBoundException {
        final Registry registry = LocateRegistry.getRegistry(1234);
        Equation equation = (Equation) registry.lookup(NAME); //получаем из регистра нужный объект по уникальному имени
//        List<Double> multiplyResult = equation.multiply(9, 6, 1);
        List<Double> multiplyResult = equation.multiply(2, 3, 1);
        System.out.println(multiplyResult);
    }
}
