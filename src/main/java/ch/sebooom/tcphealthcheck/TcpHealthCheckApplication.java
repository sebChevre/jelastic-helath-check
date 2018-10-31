package ch.sebooom.tcphealthcheck;

import ch.sebooom.tcphealthcheck.infrastructure.TcpHealthCheck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TcpHealthCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcpHealthCheckApplication.class);
    }


}
