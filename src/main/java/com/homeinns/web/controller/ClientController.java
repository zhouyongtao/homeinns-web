package com.homeinns.web.controller;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Irving on 2014/11/24.
 */
@Controller
@RequestMapping("/oauth2")
public class ClientController {

    private static Logger logger = LoggerFactory.getLogger(AuthzController.class);
    /**
     * 获得授权码
     * @return
     */
    @RequestMapping(value = "/buildOAuthzReq" ,method = RequestMethod.POST)
    public String buildOAuthzReq() {
        try {
            OAuthClientRequest oauthResponse = OAuthClientRequest
                                               .authorizationProvider(OAuthProviderType.FACEBOOK)
                                               .setClientId("fbed1d1b4b1449daa4bc49397cbe2350")
                                               .setRedirectURI("http://localhost:8080/getOAuthzToken")
                                               .buildQueryMessage();
            return "redirect:"+oauthResponse.getLocationUri();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return "redirect:/home";
    }

    /**
     * 获得令牌
     * @param code
     * @return
     */
    @RequestMapping(value = "/getOAuthzToken" ,method = RequestMethod.POST)
    public String oauth2_getToken(String code) {
        return "oauth2/login";
    }

    /*
    @RequestMapping(value = "/oauth2_login" ,method = RequestMethod.POST)
    public String oauth2_login(String returnUrl) {
        //获得令牌
        logger.info("LoginController oauth2_login ");
        return "oauth2/login";
    }
    */
}
