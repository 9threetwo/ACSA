package Practice2;

import java.rmi.RemoteException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class EquationServer implements Equation {
    @Override
    public List<Double> multiply(double a, double b, double c) throws RemoteException {
        double D = b * b - 4 * a * c;
        List<Double> res = new ArrayList<>();
        if (D > 0) {
            res.add((-b + Math.sqrt(D)) / (2 * a));
            res.add((-b - Math.sqrt(D)) / (2 * a));
        }
        if (D == 0) {
            res.add(-b / (2 * a));
        }
        return res;
    }
}
