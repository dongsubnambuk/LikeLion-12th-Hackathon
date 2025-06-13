package com.demo.nimn.dto.notification.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String userEmail;
    private int count;
}
