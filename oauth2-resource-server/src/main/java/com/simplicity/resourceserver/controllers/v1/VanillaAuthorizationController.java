package com.simplicity.resourceserver.controllers.v1;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/vanilla")
public class VanillaAuthorizationController {

    @RequestMapping(value = {"/something_to_see"})
    @ResponseBody
    public String index() {
        return "Something to See";
    }

    @RequestMapping(value = {"/authorization_code", "/authorization_code/{path:[^\\.]*}"})
    public String authorize() {
        return "forward:/vanilla/authorize.html";
    }

    @RequestMapping(value = {"/issue_token", "/issue_token/{path:[^\\.]*}"})
    public String issue() {
        return "forward:/vanilla/token.html";
    }
}

