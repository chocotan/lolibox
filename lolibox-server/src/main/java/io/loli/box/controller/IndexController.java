package io.loli.box.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author choco
 */
@Controller
public class IndexController {

    @Value("${login.social.enabled}")
    private List<String> loginProviders;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("loginProviders", loginProviders);
        return "index";
    }
}
