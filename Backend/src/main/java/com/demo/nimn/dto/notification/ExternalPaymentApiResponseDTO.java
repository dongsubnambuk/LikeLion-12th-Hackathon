package com.demo.nimn.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExternalPaymentApiResponseDTO {
    private List<String> email;

    @Builder
    public ExternalPaymentApiResponseDTO(List<String> email){
        this.email = email;
    }
}
