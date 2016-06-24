package io.loli.box.controller;

import io.loli.box.exception.UserExistsException;
import io.loli.box.service.impl.UserService;
import io.loli.box.social.User;
import org.hashids.Hashids;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author choco
 */
@Controller
public class LoginController {


    @Value("${login.social.enabled}")
    private List<String> loginProviders;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("invitationCodeHashIds")
    private Hashids hashids;


    @RequestMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("loginProviders", loginProviders);
        return "signin";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(RegisterReq registerReq) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupSubmit(@Valid RegisterReq registerReq, BindingResult bindingResult,
                               HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return signup(registerReq);
        }
        // validate invitation code
        try {
            long[] decoded = hashids.decode(registerReq.getInvitationCode());
            if (decoded.length == 0) {
                bindingResult.rejectValue("verificationCode", "Verifycation Error");
                return signup(registerReq);
            }
        } catch (Exception e) {
            bindingResult.rejectValue("verificationCode", "Verifycation Error");
            return signup(registerReq);
        }

        User registered = new User();
        registered.setEmail(registerReq.getEmail());
        registered.setUserName(registerReq.getUserName());
        registered.setPassword(registerReq.getPassword());
        try {
            userService.registerNewUser(registered);
        } catch (UserExistsException e) {
            bindingResult.rejectValue("email", e.getMessage());
            return signup(registerReq);

        }
        return "signupResult";
    }
}


class RegisterReq {
    @NotEmpty
    private String userName;
    @NotEmpty
    @Min(3)
    private String password;
    @NotEmpty
    @NotNull
    @Email
    private String email;
    @NotEmpty
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

