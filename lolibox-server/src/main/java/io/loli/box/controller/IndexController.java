package io.loli.box.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author choco
 */
@Controller
public class IndexController {

    @RequestMapping("/")

    @PreAuthorize("@adminProperties.anonymous or hasRole('USER')")
    public String index() {
        return "index";
    }
}
