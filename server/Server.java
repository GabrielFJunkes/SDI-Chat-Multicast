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

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    public static void main(String[] args) {

        int portNumber = 2222;
        if (args.length >= 1) {
            portNumber = Integer.valueOf(args[0]).intValue();
        }
        System.out.println("Listening on port: "+portNumber);

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }
        
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                new ClientThread(clientSocket).start();
            } catch (IOException e) {
                System.out.println(e);
            }
        }   
    }
}