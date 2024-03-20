package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private Multicast multicast;

    public ClientThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.multicast = Multicast.getInstance();
    }

  @SuppressWarnings("deprecation")
  public void run() {
    try {
      this.is = new DataInputStream(clientSocket.getInputStream());
      this.os = new PrintStream(clientSocket.getOutputStream());

      this.os.println("Digite a senha:");
      String token = this.is.readLine();

      if (!token.equals("senha")){
        this.os.println("Senha errada.");
        close();
      }

      this.os.println(multicast.getIp());

      this.os.println("Digite seu nome.");
      String name = this.is.readLine().trim();

      multicast.sendMessage("Usuário "+name+" entrou!");

      while (true) {
        String line = is.readLine();

        if (line.startsWith("/quit")) {
          break;
        }
        
        multicast.sendMessage("<"+name+"> - "+line);
      }
      
      multicast.sendMessage("Usuário "+name+" saiu!");

      close();
    } catch (IOException e) {
    }
  }

  private void close() throws IOException{
    this.is.close();
    this.os.close();
    this.clientSocket.close();
  }
}
