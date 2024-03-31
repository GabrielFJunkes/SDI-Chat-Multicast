/*
 * O cliente deve executar as operações com as matrizes e salvar os dados (recuperar e por fim excluir o arquivo)
 */

import java.rmi.registry.*;

public class Client {
  public static void main(String[] args) {
    if (args.length >= 2) {
      String serverMatrixIP = args[0];
      String serverDatabaseIP = args[1];
      try {
        int linhas = 3;
        int colunas = 3;
        String diretorioSumBackup = "sumBackup";
        String diretorioMultBackup = "multBackup";

        double[][] matrizA = new double[linhas][colunas];
        double[][] matrizB = new double[linhas][colunas];

        double[][] sum = new double[linhas][colunas];
        double[][] mult = new double[linhas][colunas];

        // Obtém uma referência para o registro do RMI
        Registry serverMatrixRegistry = LocateRegistry.getRegistry(serverMatrixIP, 6601);
        Registry serverDatabaseRegistry = LocateRegistry.getRegistry(serverDatabaseIP, 6600);

        // Obtém a stub do servidor
        IMatrix serverMatrixStub = (IMatrix) serverMatrixRegistry.lookup("Matrix");
        IDatabase serverDatabaseStub = (IDatabase) serverDatabaseRegistry.lookup("Database");

        // Chama o método do servidor para inicializar as matrizes
        matrizA = serverMatrixStub.randfill(linhas, colunas);
        matrizB = serverMatrixStub.randfill(linhas, colunas);
        System.out.println("Matriz A inicial: ");
        print(matrizA);
        System.out.println("Matriz B inicial: ");
        print(matrizB);

        // Chama o método do servidor para somar as matrizes e salva
        serverDatabaseStub.save(serverMatrixStub.sum(matrizA, matrizB), diretorioSumBackup);

        // Chama o método do servidor para multiplicar as matrizes e salva
        serverDatabaseStub.save(serverMatrixStub.mult(matrizA, matrizB), diretorioMultBackup);

        // recupera as matrizes e remove o arquivo de backup
        sum = serverDatabaseStub.load(diretorioSumBackup);
        serverDatabaseStub.remove(diretorioSumBackup);
        System.out.println("Resultado da soma das matrizes: ");
        print(sum);

        mult = serverDatabaseStub.load(diretorioMultBackup);
        serverDatabaseStub.remove(diretorioMultBackup);
        System.out.println("Resultado da multiplicacao das matrizes: ");
        print(mult);

      } catch (Exception ex) {
        ex.printStackTrace();
      }

    } else {
      System.out.println("IP ServerMatrix e/ou IP SeverDatabase faltando");
    }
  }

  private static void print(double[][] matrix) {
    int rowsA = matrix.length;
    int colsA = matrix[0].length;
    for (int i = 0; i < rowsA; i++) {
      for (int j = 0; j < colsA; j++) {
        System.out.print(matrix[i][j] + "\t");
      }
      System.out.println(" ");
    }
  }
}
