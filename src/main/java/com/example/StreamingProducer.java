package com.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class StreamingProducer {
	private final static String QUEUE_NAME = "streaming_notifications";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			// Notificaciones de nuevas tendencias
			String[] notifications = {
					"New Movie: 'Guardians of the Galaxy Vol. 3'",
					"New Series: 'Stranger Things' Season 5",
					"New Episode: 'The Witcher' S03E01",
					"New Series: 'Rick and Morty: The Anime",
			};

			for (String message : notifications) {
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
				System.out.println(" [x] Sent '" + message + "'");
			}
		}
	}
}
