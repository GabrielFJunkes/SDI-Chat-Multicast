package server;

public class RunServer {
    public static void main(String[] argv) throws Exception {
        int numberOfServer = 1;
        if (argv.length > 0) {
            numberOfServer = Integer.valueOf(argv[0]);
        }

        for (int i = 0; i < numberOfServer; i++) {
            new Server(String.valueOf(i)).start();
        }
        
    }
}
