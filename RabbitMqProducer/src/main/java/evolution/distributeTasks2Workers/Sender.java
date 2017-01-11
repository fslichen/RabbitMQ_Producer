package evolution.distributeTasks2Workers;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Sender {
	private static final String TASK_QUEUE_NAME = "anyQueueName1";// The producer and the consumer are communicating via a queue. Make sure that the producer and the consumer shares the same queue. 
	
	private static String getMessage(String[] strings){
	    if (strings.length < 1)
	        return "Hello World!";
	    return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuilder words = new StringBuilder(strings[0]);
	    for (int i = 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	}
	
	public static void main(String[] args)
			throws java.io.IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");// Make sure you turn on the local RabbitMq server and don't make a mistake to close the terminal.
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);// A channel corresponds to a queue.
		String[] strings = {"Hello", "World", "Goodbye", "Past"};
		String message = getMessage(strings);
		channel.basicPublish("", TASK_QUEUE_NAME,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());// The basicPublish method is responsible for sending the message.
		System.out.println("Sent '" + message + "'");
		channel.close();
		connection.close();
	}      
}
