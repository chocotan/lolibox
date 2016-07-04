package io.loli.box.controller;

import io.loli.box.AdminProperties;
import io.loli.box.exception.UserExistsException;
import io.loli.box.service.InvitationCodeService;
import io.loli.box.service.impl.UserService;
import io.loli.box.entity.Role;
import io.loli.box.entity.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author choco
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InvitationCodeService invitationCodeService;

    @Autowired
    private AdminProperties adminProperties;

    @RequestMapping("/signin")
    public String signin(Model model) {
        return "signin";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(RegisterDto registerDto) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupSubmit(@Valid RegisterDto registerDto, BindingResult bindingResult,
                               RedirectAttributes redirectAttrs, Model model) {
        if (bindingResult.hasErrors()) {
            return signup(registerDto);
        }

        if (adminProperties.isSignupInvitation()) {
            // validate invitation code
            try {
                if (!invitationCodeService.verify(registerDto.getEmail(), registerDto.getInvitationCode())) {
                    bindingResult.rejectValue("invitationCode", "invitationCode.error");
                    return signup(registerDto);
                }
            } catch (Exception e) {
                bindingResult.rejectValue("invitationCode", "invitationCode.error");
                return signup(registerDto);
            }
        }
        User registered = new User();
        registered.setRole(Role.ROLE_USER);
        registered.setEmail(registerDto.getEmail());
        registered.setUserName(registerDto.getUserName());
        registered.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        try {
            userService.registerNewUser(registered);
        } catch (UserExistsException e) {
            bindingResult.rejectValue("email", e.getMessage());
            return signup(registerDto);
        }
        redirectAttrs.addAttribute("messages", "signup.success");
        return "redirect:signin";
    }
}


class RegisterDto {
    @NotEmpty
    @NotNull
    private String userName;
    @NotEmpty
    @Length(min = 6, max = 32)
    @NotNull
    private String password;
    @NotEmpty
    @NotNull
    @Email
    private String email;
    private String invitationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}

