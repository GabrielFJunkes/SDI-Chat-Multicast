package server;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import utils.Logger;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import utils.Message;
import utils.Resource;

import com.rabbitmq.client.*;
import utils.Common;

public class Server extends Thread{
    private Logger logger;
    private Vector<Resource> resources = new Vector<>();

    private int recursos_totais;
    private int recursos_usados = 0;

    private Channel highChannel;
    private Channel lowChannel;

    public Server(String name) throws IOException, TimeoutException {
        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            this.recursos_totais = 200;
        } else {
            this.recursos_totais = 100;
        }
        this.logger = new Logger(name);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        this.highChannel = connection.createChannel();
        this.lowChannel = connection.createChannel();

        this.highChannel.queueDeclare(Common.HIGH_PRIOR_QUEUE, false, false, false, null);
        this.lowChannel.queueDeclare(Common.LOW_PRIOR_QUEUE, false, false, false, null);

        System.out.println(" Capacidade do server = " + this.recursos_totais + ". To exit press CTRL+C");
    }

    public void run() {
        while (true) {
            this.freeResources();
            try {
                GetResponse respostaHigh = highChannel.basicGet(Common.HIGH_PRIOR_QUEUE, false);
                GetResponse respostaLow = lowChannel.basicGet(Common.LOW_PRIOR_QUEUE, false);
                if(respostaHigh!=null){
                    this.runHighQueue();
                }else if(respostaLow!=null){
                    this.runLowQueue();
                }                
                /*
                if (this.highChannel.consumerCount(Common.HIGH_PRIOR_QUEUE) > 0) {
                    this.runHighQueue();
                } else if (this.lowChannel.consumerCount(Common.LOW_PRIOR_QUEUE) > 0) {
                    this.runLowQueue();
                }
                */
            } catch (IOException e) {
                System.out.println("Error reading queues.");
            }
        }
    }
    
    private void close() {
        this.logger.saveToFile();
    }

    private void freeResources() {
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < this.resources.size(); i++) {
            Resource res = this.resources.get(i);
            if (currentTime >= res.getTime()) {
                this.recursos_usados -= res.getResource();
                this.resources.remove(res);
            }
        }
    }

    private boolean executeMessage(Message message) {
        if (this.recursos_totais - (this.recursos_usados + message.getResource()) > 0) {
            this.recursos_usados += message.getResource();
            return true;
        } else {
            return false;
        }
    }

    public void addMessage(Message message) {
        int msgIndex = this.logger.addMessage(message);
        while (true) {
            if (this.executeMessage(message)) {
                this.logger.startMessage(msgIndex);
                long time = System.currentTimeMillis();
                Resource res = new Resource(time, message.getResource());
                this.resources.add(res);
                break;
            }
        }
    }

    private void runHighQueue() throws IOException {
        Consumer consumer = new DefaultConsumer(this.highChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body)
                    throws IOException {
                try {
                    Message message = Message.fromBytes(body);
                    Server.this.addMessage(message);
                    System.out.println("High Queue. Message: " + message);
                } catch (IOException | ClassNotFoundException e) {
                }
            }
        };
        this.highChannel.basicConsume(Common.HIGH_PRIOR_QUEUE, true, consumer);
    }

    private void runLowQueue() throws IOException {
        Consumer consumer = new DefaultConsumer(this.highChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body)
                    throws IOException {
                try {
                    Message message = Message.fromBytes(body);
                    Server.this.addMessage(message);
                    System.out.println("Low Queue. Message: " + message);
                } catch (IOException | ClassNotFoundException e) {
                }
            }
        };
        this.lowChannel.basicConsume(Common.LOW_PRIOR_QUEUE, true, consumer);
    }
}
