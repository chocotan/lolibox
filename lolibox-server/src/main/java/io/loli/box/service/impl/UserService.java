package io.loli.box.service.impl;

import io.loli.box.exception.UserExistsException;
import io.loli.box.social.User;
import io.loli.box.social.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author choco
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public User registerNewUser(User user) throws UserExistsException {
        if (emailExist(user.getEmail())) {
            throw new UserExistsException("email.exists");
        }

        if (userNameExist(user.getEmail())) {
            throw new UserExistsException("username.exists");
        }
        return userRepository.save(user);
    }


    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    private boolean userNameExist(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            return true;
        }
        return false;
    }

}
