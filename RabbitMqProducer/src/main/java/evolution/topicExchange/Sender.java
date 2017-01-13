package evolution.topicExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	private static final String EXCHANGE_NAME = "topicExchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String routingKey = Math.random() < 0.5 ? "male.virgin.27" : "female.virgin.single.30";
        String message = "Hello World";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        System.out.println("Sent '" + routingKey + "':'" + message + "'");
        connection.close();
    }
}
