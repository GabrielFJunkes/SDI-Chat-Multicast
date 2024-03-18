package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Multicast {
    DatagramSocket socket = null;
    DatagramPacket outPacket = null;
    byte[] outBuf;
    final int PORT = 8888;
    static Multicast instance = null;

    public static Multicast getInstance() {
        if (instance==null){
            instance = new Multicast();
        }
        return instance;
    }

    public void sendMessage(String msg) throws IOException {
        outBuf = msg.getBytes();
 
        InetAddress address = InetAddress.getByName("224.0.0.2"); // TODO tirar daqui
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
 
        socket.send(outPacket);
    }
}
