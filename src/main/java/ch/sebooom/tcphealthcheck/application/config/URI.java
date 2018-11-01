package ch.sebooom.tcphealthcheck.application.config;

import lombok.Getter;
import lombok.ToString;

@ToString
public class URI{

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
