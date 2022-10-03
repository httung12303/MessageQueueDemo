import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    try (Connection conn = factory.newConnection()) {
      Channel channel = conn.createChannel();
      channel.queueDeclare("hello-world", false, false, false, null);
      Scanner scanner = new Scanner(System.in);
      while (true) {
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        if (message.equalsIgnoreCase("SHUTDOWN")) {
          break;
        }
        message += "\nMessage sent at: " + LocalDateTime.now();
        channel.basicPublish("", "hello-world", false, false, null, message.getBytes());
        System.out.println("Message sent successfully!");
      }
      channel.close();
    }
  }
}
