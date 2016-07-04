package io.loli.box.social;

import io.loli.box.AdminProperties;
import io.loli.box.dao.UserRepository;
import io.loli.box.entity.Role;
import io.loli.box.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public RepositoryUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private AdminProperties adminProperties;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null) {
            user = repository.findByUserName(email);
        }

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }

        SocialUserDetails.Builder builder = SocialUserDetails.getBuilder()
                .id(user.getId())
                .password(user.getPassword())
                .role(user.getRole());
        if (adminProperties.getEmail() != null && adminProperties.getEmail().equals(user.getEmail())) {
            builder = builder.role(Role.ADMIN);
        }


        SocialUserDetails principal = builder.socialSignInProvider(user.getSignInProvider())
                .username(user.getUserName())
                .email(user.getEmail())
                .build();

        return principal;
    }
}