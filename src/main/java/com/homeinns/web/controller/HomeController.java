package com.homeinns.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Irving on 2014/7/6.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @RequestMapping()
    public String index(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
        String sessionId= request.getRequestedSessionId();
        session.setAttribute("key",sessionId);
        logger.info("home index page : " + sessionId);
        return "home/index";
    }

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public String hello(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
        Object obj= session.getAttribute("key");
        if(obj!=null) {
            try {
                 response.getWriter().print(obj.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/get";
    }
}