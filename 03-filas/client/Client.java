package client;
import utils.Message;
import java.util.Random;
import utils.Common;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Client {

    public static void main(String[] args) throws Exception {
        int numberOfMsg = Integer.valueOf(args[0]);
        Random rand = new Random();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Channel channel2 = connection.createChannel();

        channel.queueDeclare(Common.LOW_PRIOR_QUEUE, false, false, false, null);
        channel2.queueDeclare(Common.HIGH_PRIOR_QUEUE, false, false, false, null);

        for (int i = 0; i < numberOfMsg; i++) {

            Message newMessage = new Message();

            if(rand.nextInt(2) == 0){
                channel.basicPublish("", Common.LOW_PRIOR_QUEUE, null, newMessage.toBytes());
            }
            else {
                channel2.basicPublish("", Common.HIGH_PRIOR_QUEUE, null, newMessage.toBytes());
            }
        }
    }
}
