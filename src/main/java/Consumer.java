import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();
    channel.queueDeclare("hello-world", false, false, false, null);
    channel.basicConsume(
        "hello-world",
        true,
            (s, delivery) -> {
              String message = new String(delivery.getBody());
              System.out.println("Message received: " + message);
            },
        consumerTag -> {});
    channel.close();
    conn.close();
  }
}
