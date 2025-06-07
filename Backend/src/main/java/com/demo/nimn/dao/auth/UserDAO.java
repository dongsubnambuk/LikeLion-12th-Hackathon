package com.demo.nimn.dao.auth;

import com.demo.nimn.entity.auth.Users;

import java.util.List;

public interface UserDAO {
    public Boolean existsByEmail(String email);

    public void save(Users user);

    Users findByEmail(String email);

    List<Users> findAll();
}
