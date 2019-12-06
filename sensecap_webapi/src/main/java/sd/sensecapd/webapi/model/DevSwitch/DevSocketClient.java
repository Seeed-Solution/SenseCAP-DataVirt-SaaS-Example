package sd.sensecapd.webapi.model.DevSwitch;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.net.URI;

/*
* Socket client of switches
* */
public class DevSocketClient extends WebSocketClient {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext applicationContext;

    public DevSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        logger.info("socket connected.");
    }

    @Override
    public void onMessage(String message) {
        logger.info("socket message: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("socket close!");
    }

    @Override
    public void onError(Exception ex) {
        logger.error("socket error:" + ex.getMessage());
    }
}
