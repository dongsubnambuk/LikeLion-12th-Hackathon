package com.demo.nimn.dto.email;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private String email;
    private String message;
    private boolean isSuccess;
}
