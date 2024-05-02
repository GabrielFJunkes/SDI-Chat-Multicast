package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public class Message implements Serializable{
    private final int time;
    private final int resource;

    public Message(int time, int resource) throws Error {
        if ((time < 1 && time > 20) || (resource < 1 && resource > 10)) {
            throw new Error();
        }
        this.time = time;
        this.resource = resource;
    }

    public Message() {
        Random rand = new Random();

        this.time = rand.nextInt(20) + 1;
        this.resource = rand.nextInt(10) + 1;
    }

    public int getResource() {
        return resource;
    }

    public int getTime() {
        return time;
    }
    
    public byte[] toBytes() throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(this);
            return bos.toByteArray();
        }
    }
}
