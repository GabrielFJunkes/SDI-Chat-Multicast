package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class Multicast {
    DatagramSocket socket = null;
    DatagramPacket outPacket = null;
    byte[] outBuf;
    final int PORT = 8888;
    String multicastIp;
    InetAddress address;
    static Multicast instance = null;

    public Multicast() throws IOException {
        Random r = new Random();
        this.multicastIp = "224.0.0." + (r.nextInt(250) + 1);
        this.address = InetAddress.getByName(this.multicastIp);
        this.socket = new DatagramSocket();
    }

    public static Multicast getInstance() throws IOException {
        if (instance==null){
            instance = new Multicast();
        }
        return instance;
    }

    public String getIp() {
        return this.multicastIp;
    }

    public void sendMessage(String msg) throws IOException  {
        this.outBuf = msg.getBytes();

        this.outPacket = new DatagramPacket(this.outBuf, this.outBuf.length, this.address, PORT);
 
        this.socket.send(this.outPacket);
    }
}
