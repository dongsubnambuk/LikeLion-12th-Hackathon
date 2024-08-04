package com.example.authServer.Service;

import com.example.authServer.DAO.UserDAO;
import com.example.authServer.DTO.UserDTO;
import com.example.authServer.DTO.UserDetails;
import com.example.authServer.DTO.UsersEmailDTO;
import com.example.authServer.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, @Autowired UserDAO userDAO){
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails userSignup(UserDetails userDetails) {

        if (userDAO.existsByEmail(userDetails.getEmail())) {
            throw new IllegalStateException("동일한 이메일이 이미 존재합니다.");
        }

        if (userDetails.getName() == null || userDetails.getName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (userDetails.getEmail() == null || userDetails.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        if (userDetails.getPassword() == null || userDetails.getPassword().isEmpty()) {
            throw new IllegalArgumentException("User password cannot be null or empty");
        }

        Users user = Users.builder()
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .password(bCryptPasswordEncoder.encode(userDetails.getPassword()))
                .phoneNumber(userDetails.getPhoneNumber())
                .roadAddress(userDetails.getRoadAddress())
                .detailAddress(userDetails.getDetailAddress())
                .role("ROLE_USER")
                .build();

        userDAO.save(user);

        return UserDetails.builder()
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .phoneNumber(userDetails.getPhoneNumber())
                .roadAddress(userDetails.getRoadAddress())
                .detailAddress(userDetails.getDetailAddress())
                .build();
    }

    public boolean existsByEmail(String email){
        return userDAO.existsByEmail(email);
    }

    @Override
    public UserDetails getUserDetail(String email) {
        Users user = userDAO.findByEmail(email);

        return UserDetails.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roadAddress(user.getRoadAddress())
                .detailAddress(user.getDetailAddress())
                .build();
    }

    @Override
    public UserDetails updateUser(UserDetails user) {
        String userPassword;
        Users userEntity = userDAO.findByEmail(user.getEmail());

        if(user.getPassword() == null || user.getPassword().isEmpty()){
            userPassword = userEntity.getPassword();
        }
        else {
            userPassword = bCryptPasswordEncoder.encode(user.getPassword());
            System.out.println(userPassword);
        }
        userEntity.updateUser(user, userPassword);
        userDAO.save(userEntity);
        return user;
    }

    @Override
    public UsersEmailDTO getUsersEmail() {
        List<Users> usersList = userDAO.findAll();

        // Stream을 사용하여 변환
        List<String> emails = usersList.stream()
                .map(Users::getEmail)
                .toList();

        return UsersEmailDTO.builder()
                .email(emails)
                .build();
    }

}
