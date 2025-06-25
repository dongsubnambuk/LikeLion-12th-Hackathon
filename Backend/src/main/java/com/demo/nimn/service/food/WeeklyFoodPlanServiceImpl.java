package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.DailyFoodPlanDTO;
import com.demo.nimn.dto.food.FoodChoiceSetDTO;
import com.demo.nimn.dto.food.FoodDTO;
import com.demo.nimn.dto.food.WeeklyFoodPlanDTO;
import com.demo.nimn.entity.food.DailyFoodPlan;
import com.demo.nimn.entity.food.Food;
import com.demo.nimn.entity.food.FoodChoiceSet;
import com.demo.nimn.entity.food.WeeklyFoodPlan;
import com.demo.nimn.enums.FoodTime;
import com.demo.nimn.repository.food.FoodRepository;
import com.demo.nimn.repository.food.WeeklyFoodPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class WeeklyFoodPlanServiceImpl implements  WeeklyFoodPlanService {
    private final WeeklyFoodPlanRepository weeklyFoodPlanRepository;
    private final FoodRepository foodRepository;
    private final FoodService foodService;

    private final Random random = new Random();

    // 매주 일요일에 생섵
    @Scheduled(cron = "0 0 22 ? * SUN")
    @Override
    public WeeklyFoodPlanDTO createWeeklyPlan() {
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        if (weeklyFoodPlanRepository.existsByCurrentWeeklyFoodPlan(nextMonday)) {
            WeeklyFoodPlan weeklyFoodPlan = weeklyFoodPlanRepository.findCurrentWeeklyFoodPlan(nextMonday);
            return convertToWeeklyFoodPlanDTO(weeklyFoodPlan);
        }

        WeeklyFoodPlan weeklyFoodPlan = generateWeeklyFoodPlan(nextMonday);
        weeklyFoodPlanRepository.save(weeklyFoodPlan);
        return convertToWeeklyFoodPlanDTO(weeklyFoodPlan);
    }

    @Override
    public WeeklyFoodPlanDTO readWeeklyFoodPlan() {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        WeeklyFoodPlan weeklyFoodPlan = weeklyFoodPlanRepository.findCurrentWeeklyFoodPlan(nextMonday);

        if (weeklyFoodPlan == null) {
            return createWeeklyPlan();
        }

        return convertToWeeklyFoodPlanDTO(weeklyFoodPlan);
    }

    public WeeklyFoodPlanDTO convertToWeeklyFoodPlanDTO(WeeklyFoodPlan weeklyFoodPlan) {
        return WeeklyFoodPlanDTO.builder()
                .dailyFoodPlans(convertToDailyFoodPlansDTOList(weeklyFoodPlan.getDailyFoodPlans()))
                .startDate(weeklyFoodPlan.getStartDate())
                .endDate(weeklyFoodPlan.getEndDate())
                .build();
    }

    public List<DailyFoodPlanDTO> convertToDailyFoodPlansDTOList(List<DailyFoodPlan> dailyFoodPlans) {
        List<DailyFoodPlanDTO> dailyFoodPlansDTOS = new ArrayList<>();
        for (DailyFoodPlan dailyFoodPlan : dailyFoodPlans) {
            dailyFoodPlansDTOS.add(convertToDailyFoodPlanDTO(dailyFoodPlan));
        }
        return dailyFoodPlansDTOS;
    }

    public DailyFoodPlanDTO convertToDailyFoodPlanDTO(DailyFoodPlan dailyFoodPlan) {
        return DailyFoodPlanDTO.builder()
                .day(dailyFoodPlan.getDay())
                .foodChoiceSets(convertToFoodChoiceSetDTOList(dailyFoodPlan.getFoodChoiceSets()))
                .build();
    }

    public List<FoodChoiceSetDTO> convertToFoodChoiceSetDTOList(List<FoodChoiceSet> foodChoiceSets) {
        List<FoodChoiceSetDTO> foodChoiceSetDTOS = new ArrayList<>();
        for (FoodChoiceSet foodChoiceSet : foodChoiceSets) {
            foodChoiceSetDTOS.add(convertToFoodChoiceSetDTO(foodChoiceSet));
        }
        return foodChoiceSetDTOS;
    }

    public FoodChoiceSetDTO convertToFoodChoiceSetDTO(FoodChoiceSet foodChoiceSet) {
        return FoodChoiceSetDTO.builder()
                .foods(convertToFoodDTOList(foodChoiceSet.getFoods()))
                .foodTime(foodChoiceSet.getFoodTime())
                .build();
    }

    public List<FoodDTO> convertToFoodDTOList(List<Food> foods) {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        for (Food food : foods) {
            foodDTOS.add(foodService.convertToFoodDTO(food));
        }
        return foodDTOS;
    }

    public WeeklyFoodPlan generateWeeklyFoodPlan(LocalDate startDate) {
        List<DailyFoodPlan> dailyFoodPlans = new ArrayList<>();

        for (int day = 1; day <= 7; day++) {
            List<FoodChoiceSet> foodChoiceSets = new ArrayList<>();
            for (FoodTime foodTime : FoodTime.values()) {
                List<Food> foods = getRandomFoods(3);
                FoodChoiceSet foodChoiceSet = FoodChoiceSet.builder()
                        .foodTime(foodTime)
                        .foods(foods)
                        .build();
                foodChoiceSets.add(foodChoiceSet);
            }
            DailyFoodPlan dailyFoodPlan = DailyFoodPlan.builder()
                    .foodChoiceSets(foodChoiceSets)
                    .day(startDate.plusDays(day - 1))
                    .build();
            dailyFoodPlans.add(dailyFoodPlan);
        }

        return WeeklyFoodPlan.builder()
                .dailyFoodPlans(dailyFoodPlans)
                .startDate(startDate)
                .endDate(startDate.plusDays(6))
                .build();
    }

    public List<Food> getRandomFoods(int count) {
        List<Food> selectedFoods = new ArrayList<>();
        List<Food> foods = foodRepository.findAll();

        for (int i = 0; i < count; i++) {
            selectedFoods.add(foods.get(random.nextInt(foods.size())));
        }

        return selectedFoods;
    }
}
