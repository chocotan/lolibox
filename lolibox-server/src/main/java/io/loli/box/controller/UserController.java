package io.loli.box.controller;

import io.loli.box.dao.ImgFileRepository;
import io.loli.box.entity.Role;
import io.loli.box.entity.User;
import io.loli.box.service.impl.UserService;
import io.loli.box.social.SocialUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author choco
 */
@Controller
public class UserController {
    private Integer pageSize = 20;

    @Autowired
    private ImgFileRepository repository;


    @Autowired
    private UserService userService;


    @RequestMapping("/user/images")
    @PreAuthorize("@adminProperties.anonymous or hasRole('USER')")
    public String imagesRedirect() {
        return "redirect:/user/images?page=0&size=15";
    }


    @RequestMapping(value = "/user/images",params = "page")
    @PreAuthorize("@adminProperties.anonymous or hasRole('USER')")
    public String myImages(Model model, Authentication authentication, Pageable page) {

        User user = null;
        if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(Role.ROLE_USER.toString()::equals)) {
            user = userService.findByEmailOrName(((SocialUserDetails) authentication.getPrincipal()).getEmail());
        }
        Long id = user.getId();
        model.addAttribute("images", repository.findByUserIdOrderByCreateDateDesc(id, page));
        return "user/images";
    }
}
