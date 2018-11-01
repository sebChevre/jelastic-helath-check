package ch.sebooom.tcphealthcheck.application.config;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;

@Slf4j
@Configuration
@Profile("dev")
public class DevConfig {

    @Value("${activemq-consumer.host}")
    private String ACTIVEMQ_CONSUMER_HOST;
    @Value("${activemq-consumer.port}")
    private String ACTIVEMQ_CONSUMER_PORT;

    @Value("${activemq-producer.host}")
    private String ACTIVEMQ_PROD_HOST;
    @Value("${activemq-producer.port}")
    private String ACTIVEMQ_PRODUCER_PORT;

    @Value("${activemq-broker.host}")
    private String ACTIVEMQ_BROKER_HOST;
    @Value("${activemq-broker.port}")
    private String ACTIVEMQ_BROKER_PORT;



    @Bean
    public HashMap<String, URI> noeuds(){
        HashMap<String, URI> configuration = new HashMap<>();
        configuration.put("activemq-consumer",new URI(ACTIVEMQ_CONSUMER_HOST,"/actuator/health",Integer.valueOf(ACTIVEMQ_CONSUMER_PORT)));
        configuration.put("activemq-producer",new URI(ACTIVEMQ_PRODUCER_PORT,"/actuator/health",Integer.valueOf(ACTIVEMQ_PRODUCER_PORT)));
        configuration.put("activemq-broker",new URI(ACTIVEMQ_BROKER_HOST,Integer.valueOf(ACTIVEMQ_BROKER_PORT)));
        return configuration;
    }

}

