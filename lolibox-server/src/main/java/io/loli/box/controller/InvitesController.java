package io.loli.box.controller;

import io.loli.box.AdminProperties;
import io.loli.box.dao.InvitationCodeRepository;
import io.loli.box.entity.InvitationCode;
import io.loli.box.entity.User;
import io.loli.box.service.impl.UserService;
import io.loli.box.social.SocialUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@RequestMapping("user/invites")
@PreAuthorize("@adminProperties.anonymous or hasRole('USER')")
@Controller
public class InvitesController {
    @Autowired
    private UserService userService;

    @Autowired
    private InvitationCodeRepository repository;

    @Autowired
    private AdminProperties properties;

    @RequestMapping("create")
    public String create(Model model, Authentication authentication) {
        String email = ((SocialUserDetails) authentication.getPrincipal()).getEmail();
        User user = userService.findByEmailOrName(email);
        // 检查注册日期，n天内刚注册的不能创建(管理员除外)
        if (!email.equals(properties.getEmail())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -properties.getInvitationLimitDays());
            Date ago = cal.getTime();
            // 在这个日期后注册的，不能创建邀请码
            if (user.getCreateDate().after(ago)) {
                model.addAttribute("msg", "注册" + properties.getInvitationLimitDays() + "天内不能创建邀请码");
                return list(model, authentication);
            }
            Optional<InvitationCode> invites = repository.findByCreateUserEmailOrderByCreateDateDesc(email).stream().findFirst();
            if (invites.isPresent()) {
                // 检查上次创建日期，一周内只能创建一个
                // 获取一周前日期
                if (invites.get().getCreateDate().after(ago)) {
                    model.addAttribute("msg", properties.getInvitationLimitDays() + "天内只能创建一个邀请码");
                    return list(model, authentication);
                }
            }
        }

        InvitationCode code = new InvitationCode();
        code.setCreateDate(new Date());
        code.setCreateUser(user);
        code.setCode(UUID.randomUUID().toString().replace("-", ""));
        code.setStatus(0);
        repository.save(code);
        model.addAttribute("msg", "创建成功");
        return list(model, authentication);
    }

    @RequestMapping("list")
    public String list(Model model, Authentication authentication) {
        String email = ((SocialUserDetails) authentication.getPrincipal()).getEmail();
        model.addAttribute("invites", repository.findByCreateUserEmailOrderByCreateDateDesc(email));
        return "user/invites";
    }
}
