/** HelloClient.java **/
import java.rmi.registry.*;
public class ClientLeo {
   public static void main(String[] args) {
      String host = (args.length < 1) ? null : args[0];
      try {
         // Obtém uma referência para o registro do RMI
         Registry registry = LocateRegistry.getRegistry(host,6600);

         // Obtém a stub do servidor
         IMatrix stub= (IMatrix) registry.lookup("CalcularMatriz");

         //Chama o método soma do servidor e imprime a mensagem(certo)
         double m1[][] = {{1,1},{1,1}};
         double m2[][] = {{1,2},{3,4}};
         double result[][] = stub.sum(m1,m2);
         int rows = result.length;
         int cols = result[0].length;
         System.out.println("Soma:");
         for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(result[i][j]+" ");
            }
            System.out.println(" ");
         }

         //metodo mult
         double m3[][] = { {1, 2, 3}, 
                           {4, 5, 6}
                         };
         double m4[][] = { {7, 8}, 
                           {9, 10}, 
                           {11, 12}
                         };
         double resultM[][] = stub.mult(m3,m4);
         int rowsM = resultM.length;
         int colsM = resultM[0].length;
         System.out.println("Mult:");
         for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                System.out.print(resultM[i][j] + " ");
            }
            System.out.println(" ");
         }
      
         //Chama o método randfill do servidor e imprime matriz
         int rowsR = 5;
         int colsR = 3;
         System.out.println("Rand:");
         double resultR[][] = stub.randfill(rowsR,colsR);
         for (int i = 0; i < rowsR; i++) {
            for (int j = 0; j < colsR; j++) {
                System.out.print(resultR[i][j]+" ");
            }
            System.out.println(" ");
         }

      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }
}
