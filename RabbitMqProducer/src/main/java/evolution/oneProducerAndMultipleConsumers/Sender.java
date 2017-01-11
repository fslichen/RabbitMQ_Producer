package evolution.oneProducerAndMultipleConsumers;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	// The exchange is responsible for sending the messages from the producer to the queues.
	private static final String EXCHANGE_NAME = "logs";// Define the exchange name as logs.

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");// A fanout exchange means that it sends a message to all the queues. 
        String message = "Hello World";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());// In the previous examples, the first parameter is null, which means it uses the default exchange.
        System.out.println("Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
