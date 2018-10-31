package ch.sebooom.tcphealthcheck.infrastructure;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpHealthCheck {

    private String adress;
    private int port;

    public TcpHealthCheck(String adress, int port){
        this.adress = adress;
        this.port = port;
    }

    public boolean isNodeAlive(){

        Socket s;

        boolean available = true;
        try {

            s = new Socket(adress, port);

            if (s.isConnected())
            {
                s.close();
            }
        }
        catch (UnknownHostException e)
        { // unknown host
            available = false;
            s = null;
        }
        catch (IOException e) {
            // io exception, service probably not running
            available = false;
            s = null;
        }
        catch (NullPointerException e) {
            available = false;
            s=null;
        }


        return available;
    }
}
