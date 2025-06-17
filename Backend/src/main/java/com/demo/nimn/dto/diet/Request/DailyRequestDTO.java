package com.demo.nimn.dto.diet.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyRequestDTO {
    String userEmail;
    LocalDate date;
}
