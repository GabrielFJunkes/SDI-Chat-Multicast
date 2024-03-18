package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread {
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private Multicast multicast = Multicast.getInstance();

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

  public void run() {
    try {
      /*
       * Create input and output streams for this client.
       */
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
      os.println("Enter your name.");
      String name = is.readLine().trim();
      os.println("Hello " + name
          + " to our chat room.\nTo leave enter /quit in a new line");

      // Send Entered chat
      multicast.sendMessage("Usuário "+name+" entrou!");
      while (true) {
        String line = is.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
        // Send line
        multicast.sendMessage("<"+name+"> - "+line);
      }
      // Send Leaving chat
      multicast.sendMessage("Usuário "+name+" saiu!");

      /*
       * Close the output stream, close the input stream, close the socket.
       */
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
}
