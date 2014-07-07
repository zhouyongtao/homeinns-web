package com.homeinns.web.controller;
import org.springframework.stereotype.Controller;

/**
 * Created by Irving on 2014/7/6.
 */
@Controller
public class HomeController {

    public  String index(){

        return "I'am index page!";
    }
}
