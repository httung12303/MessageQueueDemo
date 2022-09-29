import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Producer {
  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
//    factory.setUsername("admin");
//    factory.setPassword("admin");
    try (Connection conn = factory.newConnection()) {
      Channel channel = conn.createChannel();
      channel.queueDeclare("hello-world", false, false, false, null);

      String message = "Hello world!";

      channel.basicPublish("", "hello-world", null, message.getBytes(StandardCharsets.UTF_8));
      System.out.println("Message sent");
    }
  }
}
