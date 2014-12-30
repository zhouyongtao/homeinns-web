package com.homeinns.web.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Irving on 2014/12/10.
 */
@RestController
@RequestMapping("/rest")
public class RestfulController {

    @RequestMapping(method = RequestMethod.GET)
    //@JsonView(User.WithoutPasswordView.class)
    public String test() {
        return "just rest test!";
    }
}
