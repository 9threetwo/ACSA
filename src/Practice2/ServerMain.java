package Practice2;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {
    public static final String NAME = "server.equation"; //переменная для удаленного объекта
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        final EquationServer server = new EquationServer(); //создаём объект-уравнение
        final Registry registry = LocateRegistry.createRegistry(1234); //регистр удаленных объектов; уникальный порт, по которому ищется реестр объектов
        Remote stub = UnicastRemoteObject.exportObject(server, 0); //заглушка, которая инкапсулирует весь процесс удаленного вызова
        registry.bind(NAME, stub);
    }
}