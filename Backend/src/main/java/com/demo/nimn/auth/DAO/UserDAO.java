package com.demo.nimn.auth.DAO;

import com.demo.nimn.auth.Entity.Users;

import java.util.List;

public interface UserDAO {
    public Boolean existsByEmail(String email);

    public void save(Users user);

    Users findByEmail(String email);

    List<Users> findAll();
}
