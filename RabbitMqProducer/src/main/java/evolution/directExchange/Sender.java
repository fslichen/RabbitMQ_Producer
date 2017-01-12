package evolution.directExchange;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	private static final String EXCHANGE_NAME = "directExchange";

    public static void main(String[] args)
                  throws java.io.IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");// Define the exchange as a direct exchange.
        String routingKey = Math.random() < 0.5 ? "red" : "yellow";// Set the routing key as red with probability of 0.5. The queue accepts any message from the producer with routing key equals either red or yellow because the queue binds to the exchange with binding keys of both red and yellow. 
        String message = "Hello World";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());// The routing key on the producer side corresponds to the binding key on the consumer side. 
        System.out.println("Sent '" + routingKey + "':'" + message + "'");
        channel.close();
        connection.close();
    }
}
