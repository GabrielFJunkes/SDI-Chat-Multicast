package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Multicast extends Thread{
    // vars
    // EX: private multicastIp e porta
    // se basear no Multicast.java do server, mas só pra ler

    // Multicast
    // Escuta por msg e escreve no console

    public void run() {
    MulticastSocket socket = null;
    DatagramPacket inPacket = null;
    byte[] inBuf = new byte[256];
    try {
      //Prepare to join multicast group
      socket = new MulticastSocket(8888);
      InetAddress address = InetAddress.getByName("224.0.0.2");
        //isso não vai funcionar pq os ultimos 8 bytes são aleatorios, revisar depois como testar

      socket.joinGroup(address);
 
      while (true) {
        inPacket = new DatagramPacket(inBuf, inBuf.length);
        socket.receive(inPacket);
        String msg = new String(inBuf, 0, inPacket.getLength());
        System.out.println(msg);
      }
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
  }
}
