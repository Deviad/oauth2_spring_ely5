package com.simplicity.resourceserver.controllers.v1;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/react")
public class ReactAuthorizationController {

    @RequestMapping(value = {"/", "/{[path:[^\\.]*}"})
    public String authorize() {
        return "forward:/index.html";
    }

}

