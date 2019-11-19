package com.javacodegeeks.examples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class MainController {

    @RequestMapping
    public String homepage(){
        return "index";
    }
    @RequestMapping(value = "/index")
    public String index(){
        return "/index.html";
    }
}
