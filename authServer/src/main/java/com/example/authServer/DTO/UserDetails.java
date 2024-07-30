package com.example.authServer.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private String name;
    private String email;
    private String password;

    private String phoneNumber;
    private String roadAddress;
    private String detailAddress;
}
