package ch.sebooom.tcphealthcheck.application;

import ch.sebooom.tcphealthcheck.infrastructure.HttpHealthCheck;
import ch.sebooom.tcphealthcheck.infrastructure.TcpHealthCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @Autowired
    Map<String,URI> noeuds;

    @GetMapping
    public ResponseEntity check(@RequestParam String nodeId){

        URI uri = noeuds.get(nodeId);
        log.info("HealthCheck checkNode: {}", uri);

        boolean isAlive;
        CheckResponse checkResponse;

        if(uri == null){
            checkResponse = new CheckResponse("NOT_VERIFIED","The node is not configured");
        }else {
            if(uri.isTcp()){
                isAlive  = new TcpHealthCheck(uri.getHote(),uri.getPort()).isNodeAlive();

            }else {
                isAlive = new HttpHealthCheck(uri.getHote(),uri.getPath(),uri.getPort()).isNodeAlive();
            }
            checkResponse = new CheckResponse((isAlive ? "OK" : "KO"),(isAlive ? "System UP" : "System DOWN"));

        }


        return ResponseEntity.ok(checkResponse);

    }

    @AllArgsConstructor
    @Getter
    @ToString
    class CheckResponse{

        private String status;
        private String message;

    }
}
