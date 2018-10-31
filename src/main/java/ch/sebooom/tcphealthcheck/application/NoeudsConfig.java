package ch.sebooom.tcphealthcheck.application;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Slf4j
@Configuration
public class NoeudsConfig {

    @Bean
    public HashMap<String, URI> noeuds(){
        HashMap<String, URI> configuration = new HashMap<>();
        configuration.put("activemq-consumer",new URI("localhost","/actuator/health",8801));
        configuration.put("activemq-producer",new URI("localhost","/actuator/health",8802));
        configuration.put("activemq-broker",new URI("localhost",61616));
        return configuration;
    }

}


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