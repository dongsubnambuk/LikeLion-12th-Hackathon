package com.example.authServer.Service;

import com.example.authServer.DAO.UserDAO;
import com.example.authServer.DTO.CustomUserDetails;
import com.example.authServer.Entity.Users;
import com.example.authServer.Entity.Users;
import com.example.authServer.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;

    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        //DB에서 조회
        Users userData = userDAO.findByEmail(email);

        if (userData != null) {
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
