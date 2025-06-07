package com.demo.nimn.dao.auth;

import com.demo.nimn.entity.auth.Users;
import com.demo.nimn.repository.auth.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{

    private UserRepository userRepository;

    public UserDAOImpl (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Boolean existsByEmail(String email) {
        Boolean isExist = userRepository.existsByEmail(email);

        return isExist;
    }

    @Override
    public void save(Users user) {
        userRepository.save(user);
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }
}
