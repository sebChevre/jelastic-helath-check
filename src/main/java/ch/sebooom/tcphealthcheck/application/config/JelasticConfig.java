package ch.sebooom.tcphealthcheck.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;

@Slf4j
@Configuration
@Profile("jelastic")
public class JelasticConfig {

    @Value("${activemq-consumer.host}")
    private String AMQ_CONSUMER_HOST;
    @Value("${activemq-consumer.port}")
    private String AMQ_CONSUMER_PORT;

    @Value("${activemq-producer.host}")
    private String AMQ_PRODUCER_HOST;
    @Value("${activemq-producer.port}")
    private String AMQ_PRODUCER_PORT;

    @Value("${activemq-broker.host}")
    private String AMQ_BROKER_HOST;
    @Value("${activemq-broker.port}")
    private String AMQ_BROKER_PORT;

    @Bean
    public HashMap<String, URI> noeuds(){

        HashMap<String, URI> configuration = new HashMap<>();
        configuration.put("activemq-consumer",new URI(resolveProp(AMQ_CONSUMER_HOST),"/actuator/health",Integer.valueOf(AMQ_CONSUMER_PORT)));
        configuration.put("activemq-producer",new URI(resolveProp(AMQ_PRODUCER_HOST),"/actuator/health",Integer.valueOf(AMQ_PRODUCER_PORT)));
        configuration.put("activemq-broker",new URI(resolveProp(AMQ_BROKER_HOST),Integer.valueOf(AMQ_BROKER_PORT)));
        return configuration;
    }

    private String resolveProp(String prop){
        if(prop == null){
            log.warn("HOST is null, 'localhost' will be used as default value");
            return "localhost";
        }
        return prop;
    }
}
