package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    

    public static void main(String[] args) {
        int portNumber = 2222;
        String serverIP = "";
        if (args.length >= 2) {
            serverIP = args[0];
            portNumber = Integer.valueOf(args[1]).intValue();

            try {
                Socket clientSocket = new Socket(serverIP, portNumber);
    
                String message;
                String response = "";
                String multicastIP;
    
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                //aguarda por resposta
                while(response == ""){
                    response = inFromServer.readLine();
                }
                System.out.println(response);

                //Manda senha
                message = inFromUser.readLine();
                outToServer.writeBytes(message + '\n');

                //Recebe confirmação
                multicastIP = inFromServer.readLine();
                if(multicastIP == "Senha errada."){
                    System.out.println("Senha incorreta");
                    clientSocket.close();
                }

                //Nao sei se essa parte a seguir deve ser implementada desse modo:
                
                // Multicast multicast = new Multicast(multicastIP);
                // multicast.startConnection();

                // while(true){
                //     multicast.getMessage();
                //     message = inFromUser.readLine();
                //     outToServer.writeBytes(message + '\n');
                //     if(message == "/quit"){
                //         break;
                //     }
                // }
                clientSocket.close();
                
            } catch (Exception e) {
                System.out.println(e);
            }


        }else{
            System.out.println("porta ou ip nao informados!");
        }
    }

    public String getMulticastIp() {
        return null;
        //mudar isso depois, para retornar o endereço de multicast que o server enviar
    }

}