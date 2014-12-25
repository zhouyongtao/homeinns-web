package com.homeinns.web.cxf.security;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Irving on 2014/12/12.
 */
public class ServerPasswordCallback implements CallbackHandler {

    private static final Map<String, String> userMap = new HashMap<String, String>();
    static {
        userMap.put("client", "Irving");
        userMap.put("server", "123456");
    }
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback callback = (WSPasswordCallback) callbacks[0];
        String clientUsername = callback.getIdentifier();
        String serverPassword = userMap.get(clientUsername);

        if (serverPassword != null) {
            callback.setPassword(serverPassword);
        }
    }
}