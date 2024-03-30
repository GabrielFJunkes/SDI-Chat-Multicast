
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

public class ServerDatabase implements IDatabase{
    @Override
    public void save(double[][] a, String filename) throws RemoteException {
        String file = "db/" + filename;
        try(ObjectOutputStream write= new ObjectOutputStream (new FileOutputStream(file)))
        {
            write.writeObject(a);
        } catch (IOException x) {
            System.err.println(x);
            throw new RemoteException();
        }
    }

    @Override
    public double[][] load(String filename) throws RemoteException {
        String file = "db/" + filename;
        try(ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(file)))
        {
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
