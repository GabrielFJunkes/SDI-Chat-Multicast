package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private static int portNumber = 2222;
    private static String serverIP;
    private static Socket clientSocket;
    
    public static void main(String[] args) throws IOException {
        if (args.length >= 2) {
            serverIP = args[0];
            portNumber = Integer.valueOf(args[1]).intValue();

            try {
                clientSocket = new Socket(serverIP, portNumber);
    
                String message;
                String response = "";
                String multicastIP;
    
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                // Token:
                response = inFromServer.readLine();
                System.out.println(response);

                // Senha
                message = inFromUser.readLine();
                outToServer.writeBytes(message + '\n');
                
                // Recebe confirmação
                multicastIP = inFromServer.readLine();
                if (multicastIP.equals("Senha errada.")) {
                    System.out.println(multicastIP);
                    clientSocket.close();
                    throw new Exception(multicastIP);
                }
                
                Multicast multicast = new Multicast(multicastIP);
                multicast.start();

                // Recebe pedido de nome
                response = inFromServer.readLine();
                System.out.println(response);

                // Envia nome
                message = inFromUser.readLine();
                outToServer.writeBytes(message + '\n');

                while(true){
                    message = inFromUser.readLine();
                    outToServer.writeBytes(message + '\n');
                    if(message.equals("/quit")){
                        break;
                    }
                }
                clientSocket.close();
                multicast.close();
                
            } catch (Exception e) {
                clientSocket.close();
                System.out.println(e);
            }


        }else{
            System.out.println("Porta e/ou ip nao informados!");
        }
    }

}