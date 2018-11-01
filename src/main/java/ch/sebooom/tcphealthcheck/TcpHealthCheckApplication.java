package ch.sebooom.tcphealthcheck;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class TcpHealthCheckApplication {

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(TcpHealthCheckApplication.class);

    }

    @PostConstruct
    private  void logApplicationInit() throws UnknownHostException {
        String port = environment.getProperty("server.port");
        String localHostAdress = InetAddress.getLocalHost().getHostAddress();
        String localHostName = InetAddress.getLocalHost().getHostName();

        String remoteHostAdress = InetAddress.getLoopbackAddress().getHostAddress();
        String remoteHostName = InetAddress.getLoopbackAddress().getHostName();

        log.info("Application Started at local adress: {}:{}", localHostAdress,port);
        log.info("Application Started at local nmae: {}:{}", localHostName,port);

        log.info("Application Started at remote adress: {}:{}", remoteHostAdress,port);
        log.info("Application Started at remote name: {}:{}", remoteHostName,port);

    }


}
