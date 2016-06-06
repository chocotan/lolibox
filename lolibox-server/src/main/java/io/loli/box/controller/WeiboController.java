package io.loli.box.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.weibo.api.Weibo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author choco
 */


@RestController
public class WeiboController {

    private Weibo weibo;
    private ConnectionRepository connectionRepository;

    @Inject
    public WeiboController(Weibo Weibo, ConnectionRepository connectionRepository) {
        this.weibo = Weibo;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping("hello")
    public String helloWeibo(Model model) {
        System.out.println(weibo.tokenOperations().getTokenInfo());
        return "hello";
    }
    
    
}
