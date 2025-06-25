package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.DailyFoodPlanDTO;
import com.demo.nimn.dto.food.FoodChoiceSetDTO;
import com.demo.nimn.dto.food.FoodDTO;
import com.demo.nimn.dto.food.WeeklyFoodPlanDTO;
import com.demo.nimn.entity.food.DailyFoodPlan;
import com.demo.nimn.entity.food.Food;
import com.demo.nimn.entity.food.FoodChoiceSet;
import com.demo.nimn.entity.food.WeeklyFoodPlan;
import com.demo.nimn.repository.meal.FoodRepository;
import com.demo.nimn.repository.meal.WeeklyFoodPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WeeklyFoodPlanServiceImpl implements  WeeklyFoodPlanService {
    private final WeeklyFoodPlanRepository weeklyFoodPlanRepository;
    private final FoodRepository foodRepository;

    private Map<Long, Integer> foodFrequency;
    private Map<Long, Double> weights;
    private Random random;

    @Override
    public WeeklyFoodPlanDTO createWeeklyPlan() {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        if (weeklyFoodPlanRepository.existsByCurrentWeeklyFoodPlan(nextMonday)) {
            WeeklyFoodPlan weeklyFoodPlan = weeklyFoodPlanRepository.findCurrentWeeklyFoodPlan(nextMonday);
            return toWeeklyFoodPlanDTO(weeklyFoodPlan);
        }

        initWeights();
        WeeklyFoodPlan weeklyFoodPlan = generateWeeklyFoodPlan(nextMonday);
        weeklyFoodPlanRepository.save(weeklyFoodPlan);
        return toWeeklyFoodPlanDTO(weeklyFoodPlan);
    }

    @Override
    public WeeklyFoodPlanDTO readWeeklyFoodPlan() {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        WeeklyFoodPlan weeklyFoodPlan = weeklyFoodPlanRepository.findCurrentWeeklyFoodPlan(nextMonday);

        if (weeklyFoodPlan == null) {
            return createWeeklyPlan();
        }

        return toWeeklyFoodPlanDTO(weeklyFoodPlan);
    }

    public WeeklyFoodPlanDTO toWeeklyFoodPlanDTO(WeeklyFoodPlan weeklyFoodPlan) {
        return WeeklyFoodPlanDTO.builder()
                .dailyFoodPlans(toDailyFoodPlansDTOS(weeklyFoodPlan.getDailyFoodPlans()))
                .startDate(weeklyFoodPlan.getStartDate())
                .endDate(weeklyFoodPlan.getEndDate())
                .build();
    }

    public List<DailyFoodPlanDTO> toDailyFoodPlansDTOS(List<DailyFoodPlan> dailyFoodPlans) {
        List<DailyFoodPlanDTO> dailyFoodPlansDTOS = new ArrayList<>();
        for (DailyFoodPlan dailyFoodPlan : dailyFoodPlans) {
            dailyFoodPlansDTOS.add(toDailyFoodPlanDTO(dailyFoodPlan));
        }
        return dailyFoodPlansDTOS;
    }

    public DailyFoodPlanDTO toDailyFoodPlanDTO(DailyFoodPlan dailyFoodPlan) {
        return DailyFoodPlanDTO.builder()
                .day(dailyFoodPlan.getDay())
                .foodChoiceSets(toFoodChoiceSetDTOS(dailyFoodPlan.getFoodChoiceSets()))
                .build();
    }

    public List<FoodChoiceSetDTO> toFoodChoiceSetDTOS(List<FoodChoiceSet> foodChoiceSets) {
        List<FoodChoiceSetDTO> foodChoiceSetDTOS = new ArrayList<>();
        for (FoodChoiceSet foodChoiceSet : foodChoiceSets) {
            foodChoiceSetDTOS.add(toFoodChoiceSetDTO(foodChoiceSet));
        }
        return foodChoiceSetDTOS;
    }

    public FoodChoiceSetDTO toFoodChoiceSetDTO(FoodChoiceSet foodChoiceSet) {
        return FoodChoiceSetDTO.builder()
                .foods(toFoodDTOS(foodChoiceSet.getFoods()))
                .foodType(foodChoiceSet.getMealType())
                .build();
    }

    public List<FoodDTO> toFoodDTOS(List<Food> foods) {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        for (Food food : foods) {
            foodDTOS.add(food.toFoodDTO());
        }
        return foodDTOS;
    }

    public WeeklyFoodPlan generateWeeklyFoodPlan(LocalDate startDate) {
        List<DailyFoodPlan> dailyFoodPlans = new ArrayList<>();

        for (int day = 1; day <= 7; day++) {
            List<FoodChoiceSet> foodChoiceSets = new ArrayList<>();
            for (String mealType : Arrays.asList("아침", "점심", "저녁")) {
                List<Food> foods = getRandomFoods(3);
                FoodChoiceSet foodChoiceSet = FoodChoiceSet.builder()
                        .mealType(mealType)
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
        Set<Long> usedFoodIds = new HashSet<>();

        for (int i = 0; i < count; i++) {
            Food food = selectUniqueFood(foods, usedFoodIds);
            selectedFoods.add(food);
            foodFrequency.put(food.getId(), foodFrequency.get(food.getId()) + 1);
            updateWeights();
            usedFoodIds.add(food.getId());
        }

        return selectedFoods;
    }

    private void initWeights() {
        foodFrequency = new HashMap<>();
        weights = new HashMap<>();
        random = new Random();
        List<Food> foods = foodRepository.findAll();
        for (Food food : foods) {
            foodFrequency.put(food.getId(), 0);
            weights.put(food.getId(), 1.0);
        }
    }

    private void updateWeights() {
        int maxFrequency = Collections.max(foodFrequency.values());
        for (Long foodId : foodFrequency.keySet()) {
            weights.put(foodId, (maxFrequency - foodFrequency.get(foodId)) + 1.0);
        }
    }

    public Food selectUniqueFood(List<Food> foods, Set<Long> usedFoodIds) {
        // 중복되지 않은 메뉴만 필터링합니다.
        List<Food> availableFoods = foods.stream()
                .filter(food -> !usedFoodIds.contains(food.getId()))
                .toList();

        if (availableFoods.isEmpty()) {
            throw new RuntimeException("No available unique foods to select from.");
        }

        // 가중치 총합 계산
        double totalWeight = availableFoods.stream()
                .mapToDouble(food -> weights.get(food.getId()))
                .sum();

        if (totalWeight <= 0) {
            throw new RuntimeException("Total weight is zero or negative, cannot select a food.");
        }

        // 랜덤 값 생성
        double randomValue = random.nextDouble() * totalWeight;

        // 가중치 기반 메뉴 선택
        for (Food food : availableFoods) {
            randomValue -= weights.get(food.getId());
            if (randomValue <= 0) {
                return food;
            }
        }

        // 최악의 경우를 대비한 fallback (이 경우는 발생하지 않아야 함)
        return availableFoods.get(availableFoods.size() - 1);
    }
}
