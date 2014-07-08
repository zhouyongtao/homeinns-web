package com.homeinns.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2014/7/8.
 */
@Controller
public class HelloController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @RequestMapping("/hello")
    public String hello(Model model) {
        logger.info("HelloController Hello ");
        return "/hello/hello";
    }
}
