package io.loli.box.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author choco
 */
@Controller
public class LoginPageController {


    @RequestMapping("/signin")
    private String signin() {
        return "signin";
    }


    @RequestMapping("/signup")
    private String signup() {
        return "signup";
    }

}
