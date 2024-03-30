package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Multicast extends Thread{
    private InetAddress address;
    private MulticastSocket socket;
    private DatagramPacket inPacket;
    private byte[] inBuf = new byte[256];
    private Boolean stayAlive = true;

    @SuppressWarnings("deprecation")
    public Multicast(String multicastIp) throws IOException {
        this.socket = new MulticastSocket(8888);
        this.address = InetAddress.getByName(multicastIp);
        this.socket.joinGroup(this.address);
    }

    public void run() {
        try {
            while (stayAlive) {
                this.inPacket = new DatagramPacket(this.inBuf, this.inBuf.length);
                socket.receive(this.inPacket);
                String msg = new String(this.inBuf, 0, this.inPacket.getLength());
                System.out.println(msg);
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void close() {
        stayAlive = false;
        this.socket.close();
    }
}
