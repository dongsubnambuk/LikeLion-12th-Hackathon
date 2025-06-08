package com.demo.nimn.dto.notification;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExternalDietApiResponseDTO {
    private String email;
    private String breakfast;
    private String lunch;
    private String dinner;

    @Builder
    public ExternalDietApiResponseDTO(String email, String breakfast, String lunch, String dinner) {
        this.email = email;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }
}