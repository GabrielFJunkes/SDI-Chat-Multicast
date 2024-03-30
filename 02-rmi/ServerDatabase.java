
/*
 * O servidor deve oferecer:
 * - Operações com a base de dados (implementando IDatabase)
  */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerDatabase implements IDatabase {
    private ServerDatabase() {
    }

    public static void main(String[] args) {
        try {
            ServerDatabase server = new ServerDatabase();

            IDatabase stub = (IDatabase) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(6600);

            registry.bind("Database", stub);

            System.out.println("Servidor pronto");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(double[][] a, String filename) throws RemoteException {
        String file = "db/" + filename;
        try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file))) {
            write.writeObject(a);
        } catch (IOException x) {
            System.err.println(x);
            throw new RemoteException();
        }
    }

    @Override
    public double[][] load(String filename) throws RemoteException {
        String file = "db/" + filename;
        try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(file))) {
            double[][] data = (double[][]) inFile.readObject();
            return data;
        } catch (IOException x) {
            System.err.println(x);
            throw new RemoteException();
        } catch (ClassNotFoundException x) {
            System.err.println(x);
            throw new RemoteException();
        }
    }

    @Override
    public void remove(String filename) throws RemoteException {
        String file = "db/" + filename;

    }
}
