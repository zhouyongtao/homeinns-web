package com.homeinns.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Irving on 2014/7/6.
 */
@Controller

public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @RequestMapping("/home")
    public String index() {
        logger.info("home index page! ");
        return "home";
    }

//    @RequestMapping(value = "/hello" ,method = RequestMethod.GET)
//    public String hello( ) {
//        return "/hello";
//    }
}