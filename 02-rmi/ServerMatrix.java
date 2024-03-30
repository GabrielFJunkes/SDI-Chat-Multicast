import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.File;
import java.lang.Math;

public class ServerMatrix  implements IMatrix{
    public ServerMatrix() {}
   // main()
   // hello()

   public static void main(String[] args) {
      try {
         // Instancia o objeto servidor e a sua stub
         ServerMatrix server = new ServerMatrix();
         IMatrix stub = (IMatrix) UnicastRemoteObject.exportObject(server, 0);
         Registry registry = LocateRegistry.createRegistry(6601);
         registry.bind("Matrix", stub);
         File theDir = new File("db");
         if (!theDir.exists()){
            theDir.mkdirs();
         }
         System.out.println("Servidor pronto");

      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public double[][] sum(double[][] a, double[][] b) throws RemoteException {
      //adicionar exeções depois
      int rowsA = a.length;
      int colsA = a[0].length;
      int rowsB = b.length;
      int colsB = b[0].length;
      if(rowsA==rowsB && colsA==colsB){
         double[][] result = new double[rowsA][rowsA];
         System.out.println("Executando sum()");
         for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
               result[i][j] = a[i][j] + b[i][j];
               System.out.print(result[i][j] + " ");
            }
            System.out.println(" ");
         }         
         return result;
      }else{
         System.out.println("matrizes de tamanhos diferentes");
         return null;
      }
      
   }

   public double[][] mult(double[][] a, double[][] b) throws RemoteException {
      //adicionar exeções depois
      int m = a.length;
      int n = a[0].length;
      int p = b[0].length;

      // Verifica se a multiplicação é possível
      if (n != b.length) {
         throw new IllegalArgumentException("Número de colunas de uma matriz deve ser igual ao número de linhas da outra matriz.");
      }
      double[][] result = new double[m][p];
      System.out.println("Executando mult()");
      for (int i = 0; i < m; i++) {
         for (int j = 0; j < p; j++) {
            for (int k = 0; k < n; k++) {
               result[i][j] += a[i][k] * b[k][j];
            }
            System.out.print(result[i][j]+ " ");
         }
         System.out.println(" ");
      }
      
      return result;
   }

   public double[][] randfill(int rows, int cols){
      double[][] result = new double[rows][cols];
      System.out.println("Executando rand()");
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
            double randomNumber = Math.random();
            result[i][j] = Math.ceil(randomNumber*10);
            System.out.print(result[i][j] + " ");
         }
         System.out.println(" ");
      }    
      return result;
   }
      
}