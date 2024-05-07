package server;

import java.util.Vector;

public class RunServer {
    public static void main(String[] argv) throws Exception {
        int numberOfServer = 1;
        if (argv.length > 0) {
            numberOfServer = Integer.valueOf(argv[0]);
        }

        Vector<Server> servers = new Vector<Server>();

        for (int i = 0; i < numberOfServer; i++) {
            Server server = new Server(String.valueOf(i));
            server.start();
            servers.add(server);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Server server : servers) {
                server.close();
            }
        }));

    }
}
