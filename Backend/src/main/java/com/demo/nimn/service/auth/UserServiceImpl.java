package com.demo.nimn.service.auth;

import com.demo.nimn.dto.auth.UserDetails;
import com.demo.nimn.dto.auth.UsersEmailDTO;
import com.demo.nimn.entity.auth.Users;
import com.demo.nimn.repository.auth.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, @Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails userSignup(UserDetails userDetails) {

        if (userRepository.existsByEmail(userDetails.getEmail())) {
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

        userRepository.save(user);

        return UserDetails.builder()
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .phoneNumber(userDetails.getPhoneNumber())
                .roadAddress(userDetails.getRoadAddress())
                .detailAddress(userDetails.getDetailAddress())
                .build();
    }

    @Override
    public boolean userLogout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0); // 브라우저에 삭제 요청
                    response.addCookie(cookie);
                }
            }
            return true;
        }
        return false;
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails getUserDetail(String email) {
        Users user = userRepository.findByEmail(email);

        if (user == null) {
            // null이면 빈 UserDetails 리턴하거나 메시지를 포함한 기본 객체 반환
            return UserDetails.builder()
                    .name(null)
                    .email(email)
                    .phoneNumber(null)
                    .roadAddress(null)
                    .detailAddress(null)
                    .build();
        }

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
        Users userEntity = userRepository.findByEmail(user.getEmail());

        if(user.getPassword() == null || user.getPassword().isEmpty()){
            userPassword = userEntity.getPassword();
        }
        else {
            userPassword = bCryptPasswordEncoder.encode(user.getPassword());
            System.out.println(userPassword);
        }
        userEntity.updateUser(user, userPassword);
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public UsersEmailDTO getAllUsersEmail() {
        List<Users> usersList = userRepository.findAll();

        // Stream을 사용하여 변환
        List<String> emails = usersList.stream()
                .map(Users::getEmail)
                .toList();

        return UsersEmailDTO.builder()
                .email(emails)
                .build();
    }

}
