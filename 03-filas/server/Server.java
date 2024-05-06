package server;
import java.util.concurrent.TimeUnit;
import utils.Logger;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import utils.Message;
import utils.Resource;

import com.rabbitmq.client.*;
import utils.Common;
import java.util.ArrayList;//vetor

public class Server extends Thread{
    private Logger logger;
    private Vector<Resource> resources = new Vector<>();

    private int recursos_totais;
    private int recursos_usados = 0;

    private Channel highChannel;
    private Channel lowChannel;

    public Server(String name) {
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
            if (this.highChannel.consumerCount(Common.HIGH_PRIOR_QUEUE) > 0) {
                this.runHighQueue();
            } else if (this.lowChannel.consumerCount(Common.LOW_PRIOR_QUEUE) > 0) {
                this.runLowQueue();
            }
        }
    }

    private void freeResources() {
        long currentTime = System.currentTimeMillis();
        Vector<Resource> toRemove = new Vector<Resource>();
        for (Resource res : this.resources) {
            if (currentTime >= res.getTime()) {
                toRemove.add(res);
                this.recursos_usados -= res.getResource();
            }
        }
        this.resources.removeAll(toRemove);
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
                break;
            }
        }
    }

    private void runHighQueue() {
        Consumer consumer = new DefaultConsumer(this.highChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body)
                    throws IOException {
                try {
                    Message message = Message.fromBytes(body);
                    this.addMessage(message);
                } catch (IOException | ClassNotFoundException e) {
                }
            }
        };
        this.highChannel.basicConsume(Common.HIGH_PRIOR_QUEUE, true, consumer);
    }

    private boolean runLowQueue() {
        Consumer consumer = new DefaultConsumer(this.highChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body)
                    throws IOException {
                try {
                    Message message = Message.fromBytes(body);
                    this.addMessage(message);
                } catch (IOException | ClassNotFoundException e) {
                }
            }
        };
        this.lowChannel.basicConsume(Common.LOW_PRIOR_QUEUE, true, consumer);
    }
}
