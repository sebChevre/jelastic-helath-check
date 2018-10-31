package ch.sebooom.tcphealthcheck.application;

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
public class NoeudsConfig {

    @Value("${activemq-consumer.host}")
    private String ACTIVEMQ_CONS_HOST;
    @Value("${activemq-producer.host}")
    private String ACTIVEMQ_PROD_HOST;
    @Value("${activemq-broker.host}")
    private String ACTIVEMQ_BROKER_HOST;



    @Bean
    public HashMap<String, URI> noeuds(){
        HashMap<String, URI> configuration = new HashMap<>();
        configuration.put("activemq-consumer",new URI(ACTIVEMQ_CONS_HOST,"/actuator/health",8801));
        configuration.put("activemq-producer",new URI(ACTIVEMQ_PROD_HOST,"/actuator/health",8802));
        configuration.put("activemq-broker",new URI(ACTIVEMQ_BROKER_HOST,61616));
        return configuration;
    }

}

@ToString
class URI{

    enum PROTOCOLE{
        HTTP,TCP
    }

    @Getter
    private String hote;
    @Getter
    private String path;
    @Getter
    private int port;

    //Defaut tcp
    private PROTOCOLE protocole = PROTOCOLE.TCP;


    public URI(String hote, String path, int port) {
        this.hote = hote;
        this.path = path;
        this.port = port;
    }

    public URI(String hote, int port) {
        this.hote = hote;
        this.port = port;
    }

    public URI tcp(){
        this.protocole = PROTOCOLE.TCP;
        return this;
    }

    public URI http() {
        this.protocole = PROTOCOLE.HTTP;
        return this;
    }

    public boolean isTcp(){
        return this.protocole == PROTOCOLE.TCP;
    }
}