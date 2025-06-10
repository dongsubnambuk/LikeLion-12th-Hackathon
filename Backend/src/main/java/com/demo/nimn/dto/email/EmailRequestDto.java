package com.demo.nimn.dto.email;


import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {
    private String email;
    private int code;
}
