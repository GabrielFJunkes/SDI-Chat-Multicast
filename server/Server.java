package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Listen TCP
    // - Abrir thread pra cada client
    // - Verificar token
    // - Enviar msg de autenticacao com ip ou msg de erro
    // - Espera por msg
    //      - Enviar no multcast

    // Multicast
    // Espera pedido de envio e envia


    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket clientSocket = null;
    public static void main(String[] args) {

        int portNumber = 2222;
        if (args.length >= 1) {
            portNumber = Integer.valueOf(args[0]).intValue();
        }
        System.out.println("Listening on port: "+portNumber);

        /*
        * Open a server socket on the portNumber (default 2222). Note that we can
        * not choose a port less than 1023 if we are not privileged users (root).
        */
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        /*
        * Create a client socket for each connection and pass it to a new client
        * thread.
        */
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                new ClientThread(clientSocket).run();
            } catch (IOException e) {
                System.out.println(e);
            }
        }   
    }
}