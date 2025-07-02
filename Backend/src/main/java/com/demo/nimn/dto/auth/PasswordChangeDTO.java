package com.demo.nimn.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}
