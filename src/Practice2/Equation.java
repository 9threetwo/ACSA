package Practice2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Equation extends Remote {
    List<Double> multiply(double a, double b, double c) throws RemoteException;
}
