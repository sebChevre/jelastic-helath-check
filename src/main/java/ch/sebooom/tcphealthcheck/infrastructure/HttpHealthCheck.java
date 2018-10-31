package ch.sebooom.tcphealthcheck.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpHealthCheck {

    private String hote;
    private String path;
    private int port;

    @Autowired
    RestTemplate restTemplate;

    public HttpHealthCheck(String hote, String path, int port){
        this.hote = hote;
        this.path = path;
        this.port = port;
    }

    public boolean isNodeAlive(){

        restTemplate.getForObject(hote.concat(path),String.class);

        return restTemplate != null;

    }
}
