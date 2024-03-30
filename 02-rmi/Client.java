/*
 * O cliente deve executar as operações com as matrizes e salvar os dados (recuperar e por fim excluir o arquivo)
 */

import java.rmi.registry.*;

public class Client {
 public static void main(String[] args) {
      if(args.length >= 2){
        String serverMatrix = args[0];
        String serverDatabase = args[1];
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
            Registry serverMatrixRegistry = LocateRegistry.getRegistry(serverMatrix,6600);
            Registry serverDatabaseRegistry = LocateRegistry.getRegistry(serverDatabase, 6601);

            // Obtém a stub do servidor
            IMatrix serverMatrixStub= (IMatrix) serverMatrixRegistry.lookup("Matrix");
            IDatabase serverDatabaseStub = (IDatabase) serverDatabaseRegistry.lookup("Database");

            // Chama o método do servidor para inicializar as matrizes
            matrizA = serverMatrixStub.randfill(linhas, colunas);
            matrizB = serverMatrixStub.randfill(linhas, colunas);

            // Chama o método do servidor para somar as matrizes e salva
            serverDatabaseStub.save(serverMatrixStub.sum(matrizA, matrizB), diretorioSumBackup);

            // Chama o método do servidor para multiplicar as matrizes e salva
            serverDatabaseStub.save(serverMatrixStub.mult(matrizA, matrizB), diretorioMultBackup);

            //recupera as matrizes e remove o arquivo de backup
            sum = serverDatabaseStub.load(diretorioSumBackup);
            serverDatabaseStub.remove(diretorioSumBackup);

            mult = serverDatabaseStub.load(diretorioMultBackup);
            serverDatabaseStub.remove(diretorioMultBackup);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

      }else{
        System.out.println("IP ServerMatrix e/ou IP SeverDatabase faltando");
      }
   }   
}
