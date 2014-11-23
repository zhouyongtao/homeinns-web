package com.homeinns.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * OAuth2 用户登录
 * Created by Irving on 2014/11/23.
 */
@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {

    private static Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public String login(String code) {
        logger.info("oauth2 login code : "+code);
        return "oauth2/login";
    }
}
