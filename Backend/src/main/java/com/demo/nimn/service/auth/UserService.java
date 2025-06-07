package com.demo.nimn.service.auth;

import com.demo.nimn.dto.auth.UserDetails;
import com.demo.nimn.dto.auth.UsersEmailDTO;

public interface UserService {

    public UserDetails userSignup(UserDetails userDetails);

    public boolean existsByEmail(String email);

    public UserDetails getUserDetail(String email);

    public UserDetails updateUser(UserDetails user);

    public UsersEmailDTO getUsersEmail();

}
