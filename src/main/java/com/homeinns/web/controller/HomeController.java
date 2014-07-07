package com.homeinns.web.controller;
import org.springframework.stereotype.Controller;

/**
 * Created by Irving on 2014/7/6.
 * http://projects.spring.io/spring-framework/
 */
@Controller
public class HomeController {

    public  String index(){

        return "I'am index page!";
    }
}
