package com.homeinns.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Irving on 2014/7/6.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @RequestMapping("/")
    public String index() {
        logger.info("home index page! ");
        return "/index";
    }

    @RequestMapping(value = "/hello" ,method = RequestMethod.GET)
    public String hello( ) {
        return "/hello";
    }
}