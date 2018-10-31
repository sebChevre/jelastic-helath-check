package ch.sebooom.tcphealthcheck.application;

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

    @Value("#{systemProperties['activemq-consumer.host']}")
    private String AMQ_CONS_HOST;
    @Value("#{systemProperties['activemq-producer.host']}")
    private String AMQ_PROD_HOST;
    @Value("#{systemProperties['activemq-broker.host']}")
    private String AMQ_BROKER_HOST;

    @Bean
    public HashMap<String, URI> noeuds(){

        HashMap<String, URI> configuration = new HashMap<>();
        configuration.put("activemq-consumer",new URI(resolveProp(AMQ_CONS_HOST),"/actuator/health",8080));
        configuration.put("activemq-producer",new URI(resolveProp(AMQ_PROD_HOST),"/actuator/health",8080));
        configuration.put("activemq-broker",new URI(resolveProp(AMQ_BROKER_HOST),8080));
        return configuration;
    }

    private String resolveProp(String prop){
        if(prop == null){
            log.warn("HOST is null, 'localhost' will be used as defautl value");
            return "localhost";
        }
        return prop;
    }
}
