package io.loli.box.dao;

import io.loli.box.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    public User findByUserName(String userName);

    User findByEmailOrUserName(String email, String name);
}