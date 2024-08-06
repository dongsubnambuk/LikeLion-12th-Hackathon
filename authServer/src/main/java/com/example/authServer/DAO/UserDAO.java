package com.example.authServer.DAO;

import com.example.authServer.Entity.Users;
import com.example.authServer.Entity.Users;
import com.example.authServer.Repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDAO {
    public Boolean existsByEmail(String email);

    public void save(Users user);

    Users findByEmail(String email);

    List<Users> findAll();
}
