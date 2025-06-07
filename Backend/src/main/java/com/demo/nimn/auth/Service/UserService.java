package com.demo.nimn.auth.Service;

import com.demo.nimn.auth.DTO.UserDetails;
import com.demo.nimn.auth.DTO.UsersEmailDTO;

public interface UserService {

    public UserDetails userSignup(UserDetails userDetails);

    public boolean existsByEmail(String email);

    public UserDetails getUserDetail(String email);

    public UserDetails updateUser(UserDetails user);

    public UsersEmailDTO getUsersEmail();

}
