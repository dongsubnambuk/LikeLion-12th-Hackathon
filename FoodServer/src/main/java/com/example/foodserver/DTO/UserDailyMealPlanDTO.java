package com.example.foodserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDailyMealPlanDTO {
    LocalDate date; // 0803~0809 날짜에 맞는 date 넣어주면 됨 (1주일치를 List 형태로 만들어서 post형태로 보내주면 됨)
    // 유저 식단 선택 -> post 요청 ->
    List<Long> foodMenuIds; // 아점저 순서대로 보내주면 됨
}