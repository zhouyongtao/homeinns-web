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
import java.io.PrintWriter;

/**
 * Created by Irving on 2014/7/6.
 */
@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * 首页
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String login(HttpServletRequest request,HttpSession session) {
        String sessionId= request.getRequestedSessionId();
        session.setAttribute("key",sessionId);
        logger.info("home index page : " + sessionId);
        return "home/index";
    }

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public void hello(HttpSession session,HttpServletResponse response) {
        PrintWriter out = null;
        try
        {
            response.setContentType("text/json;charset=utf-8");
            out = response.getWriter();
            Object obj= session.getAttribute("key");
            if(obj!=null) {
                out.write(obj.toString());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != out){ out.close();}
        }
    }
}