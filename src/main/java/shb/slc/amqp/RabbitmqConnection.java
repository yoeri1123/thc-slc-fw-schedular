package shb.slc.amqp;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;

public class RabbitmqConnection {

    @Value("${rabbitmq.host}")
    private static String host;

    @Value("${rabbitmq.port}")
    private static int port;

    @Value("${rabbitmq.username}")
    private static String username;

    @Value("${rabbitmq.password}")
    private static String pwd;

    public static Connection getConnection() {
        Connection conn = null;
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(host);
            factory.setPassword(pwd);
            conn = factory.newConnection();
        } catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

}
