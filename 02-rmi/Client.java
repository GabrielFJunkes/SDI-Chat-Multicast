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
        String diretorioSumBackup = "sumBackup.txt";
        String diretorioMultBackup = "multBackup.txt";

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
        System.out.println("Matriz A inicial: " + matrizA);
        System.out.println("Matriz B inicial: " + matrizB);

        // Chama o método do servidor para somar as matrizes e salva
        serverDatabaseStub.save(serverMatrixStub.sum(matrizA, matrizB), diretorioSumBackup);

        // Chama o método do servidor para multiplicar as matrizes e salva
        serverDatabaseStub.save(serverMatrixStub.mult(matrizA, matrizB), diretorioMultBackup);

        // recupera as matrizes e remove o arquivo de backup
        sum = serverDatabaseStub.load(diretorioSumBackup);
        serverDatabaseStub.remove(diretorioSumBackup);
        System.out.println("Resultado da soma das matrizes: " + sum);

        mult = serverDatabaseStub.load(diretorioMultBackup);
        serverDatabaseStub.remove(diretorioMultBackup);
        System.out.println("Resultado da multiplicacao das matrizes: " + mult);

      } catch (Exception ex) {
        ex.printStackTrace();
      }

    } else {
      System.out.println("IP ServerMatrix e/ou IP SeverDatabase faltando");
    }
  }
}
