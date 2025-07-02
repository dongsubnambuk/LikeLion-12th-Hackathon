package com.demo.nimn.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Schema(description = "로그인 email", example = "asd1234@naver.com")
    private String email;
    @Schema(description = "로그인 password", example = "asd1234")
    private String password;
}
