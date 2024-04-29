package client;
import utils.Message
import java.util.Random;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Client {
    private final static String LOW_PRIOR_QUEUE = "filinha"
    private final static String HIGH_PRIOR_QUEUE = "filhona"

    public static void main(String[] argv) throws Exception {
        String numberOfMsg = args[0];

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(LOW_PRIOR_QUEUE, false, false, false, null);
        channel.queueDeclare(HIGH_PRIOR_QUEUE, false, false, false, null);

        for (int i = 0; i < numberOfMsg; i++) {

            Message newMessage = new Message();

            if(rand.nextInt(2) == 0){
                channel.basicPublish("", LOW_PRIOR_QUEUE, null, newMessage);
            }
            else {
                channel.basicPublish("", HIGH_PRIOR_QUEUE, null, newMessage);
            }
        }
    }
}
