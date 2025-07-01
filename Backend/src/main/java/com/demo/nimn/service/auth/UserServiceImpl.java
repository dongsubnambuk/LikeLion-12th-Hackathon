package com.demo.nimn.service.auth;

import com.demo.nimn.dto.auth.PasswordChangeDTO;
import com.demo.nimn.dto.auth.UserDTO;
import com.demo.nimn.dto.auth.UserDetails;
import com.demo.nimn.dto.auth.UsersEmailDTO;
import com.demo.nimn.entity.auth.Users;
import com.demo.nimn.repository.auth.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, @Autowired UserRepository userRepository) {
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
        Users userEntity = userRepository.findByEmail(user.getEmail());

        userEntity.updateUser(user);
        userRepository.save(userEntity);
        return user;
    }

    @Transactional
    @Override
    public UserDTO changePassword(PasswordChangeDTO passwordChangeDto) {
        String email = passwordChangeDto.getEmail();
        String oldPassword = passwordChangeDto.getOldPassword();
        String newPassword = passwordChangeDto.getNewPassword();

        UserDTO user = UserDTO.builder()
                .email(email)
                .build();

        Users userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        // oldPassword가 일치함
        if(bCryptPasswordEncoder.matches(oldPassword, userEntity.getPassword())){
            if(bCryptPasswordEncoder.matches(newPassword, userEntity.getPassword())){
                user = user.toBuilder()
                        .password("중복")
                        .build();
                return user;
            }
            // 새로운 비밀번호를 encode 한 이후 set
            userEntity.setPassword(bCryptPasswordEncoder.encode(passwordChangeDto.getNewPassword()));
        }
        // oldPassword가 일치하지 않음
        else{
            user = user.toBuilder()
                    .password("불일치")
                    .build();
        }
        return user;
    }

    @Transactional
    @Override
    public UserDTO updatePassword(PasswordChangeDTO passwordChangeDto) {
        String email = passwordChangeDto.getEmail();
        String newPassword = passwordChangeDto.getNewPassword();

        UserDTO user = UserDTO.builder()
                .email(email)
                .build();

        Users userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        if(bCryptPasswordEncoder.matches(newPassword, userEntity.getPassword())){
            user = user.toBuilder()
                    .password("중복")
                    .build();
            return user;
        }
        // 새로운 비밀번호를 encode 한 이후 set
        userEntity.setPassword(bCryptPasswordEncoder.encode(passwordChangeDto.getNewPassword()));

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
