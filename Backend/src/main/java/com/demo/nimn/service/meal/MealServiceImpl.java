package com.demo.nimn.service.meal;

import com.demo.nimn.dao.meal.FoodDAO;
import com.demo.nimn.dao.meal.WeeklyMealPlanDAO;
import com.demo.nimn.dto.meal.*;
import com.demo.nimn.entity.meal.*;
import com.demo.nimn.service.ai.AiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {
    private final FoodDAO foodDAO;
    private final WeeklyMealPlanDAO weeklyMealPlanDAO;
    private final AiService aiService;
    private final Logger logger = LoggerFactory.getLogger(MealServiceImpl.class);

    private Map<Long, Integer> menuFrequency;
    private Map<Long, Double> weights;
    private Random random;

    @Autowired
    public MealServiceImpl(FoodDAO foodDAO,
                           WeeklyMealPlanDAO weeklyMealPlanDAO,
                           AiService aiService) {
        this.foodDAO = foodDAO;
        this.weeklyMealPlanDAO = weeklyMealPlanDAO;
        this.aiService = aiService;
    }

    @Override
    public FoodMenuDTO createMeal(String price) {
        String mealPlanText = aiService.generateFood(price);

        FoodMenu foodMenu = toFoodMenuEntity(mealPlanText);

        if (foodMenu == null) {
            throw new IllegalStateException("응답을 FoodMenu 엔티티로 변환하는 데 실패했습니다.");
        }

        foodDAO.createMeal(foodMenu);

        return toFoodMenuDTO(foodMenu);
    }

    //    @Scheduled(cron = "0 0 0 ? * MON")
    @Override
    public WeeklyMealPlanDTO createWeeklyMealPlan() {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        if (weeklyMealPlanDAO.existsByCurrentWeeklyMealPlan(nextMonday)) {
            WeeklyMealPlan weeklyMealPlan = weeklyMealPlanDAO.findCurrentWeeklyMealPlan(nextMonday);
            return toWeeklyMealPlanDTO(weeklyMealPlan);
        }

        initWeights();
        WeeklyMealPlan weeklyMealPlan = generateWeeklyMealPlan(nextMonday);
        weeklyMealPlanDAO.createWeeklyMealPlan(weeklyMealPlan);
        return toWeeklyMealPlanDTO(weeklyMealPlan);
    }

    @Override
    public WeeklyMealPlanDTO readWeeklyMealPlan() {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        WeeklyMealPlan weeklyMealPlan = weeklyMealPlanDAO.findCurrentWeeklyMealPlan(nextMonday);

        if (weeklyMealPlan == null) {
            return createWeeklyMealPlan();
        }

        return toWeeklyMealPlanDTO(weeklyMealPlan);
    }

    @Override
    public FoodMenu readFoodMenuByFoodMenuId(Long foodMenuId) {
        return foodDAO.findById(foodMenuId);
    }

    @Override
    public FoodMenuDTO readFoodMenuDTOByFoodMenuId(Long foodMenuId) {
        FoodMenu foodMenu = foodDAO.findById(foodMenuId);
        return toFoodMenuDTO(foodMenu);
    }

    @Override
    public List<FoodMenuDTO> readAll() {
        List<FoodMenu> foodMenus = foodDAO.findAll();
        return toFoodMenuDTOS(foodMenus);
    }

    private void initWeights() {
        menuFrequency = new HashMap<>();
        weights = new HashMap<>();
        random = new Random();
        List<FoodMenu> foodMenus = foodDAO.findAll();
        for (FoodMenu foodMenu : foodMenus) {
            menuFrequency.put(foodMenu.getId(), 0);
            weights.put(foodMenu.getId(), 1.0);
        }
    }

    private void updateWeights() {
        int maxFrequency = Collections.max(menuFrequency.values());
        for (Long foodMenuId : menuFrequency.keySet()) {
            weights.put(foodMenuId, (maxFrequency - menuFrequency.get(foodMenuId)) + 1.0);
        }
    }

    public List<FoodMenu> getRandomMenus(int count) {
        List<FoodMenu> selectedFoodMenus = new ArrayList<>();
        List<FoodMenu> foodMenus = foodDAO.findAll();
        Set<Long> usedMenuIds = new HashSet<>();

        for (int i = 0; i < count; i++) {
            FoodMenu foodMenu = selectUniqueMenu(foodMenus, usedMenuIds);
            selectedFoodMenus.add(foodMenu);
            menuFrequency.put(foodMenu.getId(), menuFrequency.get(foodMenu.getId()) + 1);
            updateWeights();
            usedMenuIds.add(foodMenu.getId());
        }

        return selectedFoodMenus;
    }

    public FoodMenu selectUniqueMenu(List<FoodMenu> foodMenus, Set<Long> usedMenuIds) {
        // 중복되지 않은 메뉴만 필터링합니다.
        List<FoodMenu> availableMenus = foodMenus.stream()
                .filter(menu -> !usedMenuIds.contains(menu.getId()))
                .collect(Collectors.toList());

        if (availableMenus.isEmpty()) {
            throw new RuntimeException("No available unique menus to select from.");
        }

        // 가중치 총합 계산
        double totalWeight = availableMenus.stream()
                .mapToDouble(menu -> weights.get(menu.getId()))
                .sum();

        if (totalWeight <= 0) {
            throw new RuntimeException("Total weight is zero or negative, cannot select a menu.");
        }

        // 랜덤 값 생성
        double randomValue = random.nextDouble() * totalWeight;

        // 가중치 기반 메뉴 선택
        for (FoodMenu foodMenu : availableMenus) {
            randomValue -= weights.get(foodMenu.getId());
            if (randomValue <= 0) {
                return foodMenu;
            }
        }

        // 최악의 경우를 대비한 fallback (이 경우는 발생하지 않아야 함)
        return availableMenus.get(availableMenus.size() - 1);
    }

    public WeeklyMealPlan generateWeeklyMealPlan(LocalDate startDate) {
        List<DailyMealPlan> dailyMealPlans = new ArrayList<>();

        for (int day = 1; day <= 7; day++) {
            List<MealOption> mealOptions = new ArrayList<>();
            for (String mealType : Arrays.asList("아침", "점심", "저녁")) {
                List<FoodMenu> foodMenus = getRandomMenus(3);
                MealOption mealOption = MealOption.builder()
                        .mealType(mealType)
                        .foodMenus(foodMenus)
                        .build();
                mealOptions.add(mealOption);
            }
            DailyMealPlan dailyMealPlan = DailyMealPlan.builder()
                    .mealOptions(mealOptions)
                    .day(startDate.plusDays(day - 1))
                    .build();
            dailyMealPlans.add(dailyMealPlan);
        }

        WeeklyMealPlan weeklyMealPlan = WeeklyMealPlan.builder()
                .dailyMealPlans(dailyMealPlans)
                .startDate(startDate)
                .endDate(startDate.plusDays(6))
                .build();

        return weeklyMealPlan;
    }

    public FoodMenuDTO toFoodMenuDTO(FoodMenu foodMenu) {
        return FoodMenuDTO.builder()
                .id(foodMenu.getId())
                .name(foodMenu.getName())
                .image(foodMenu.getImage())
                .price(foodMenu.getPrice())
                .main1(foodMenu.getMain1())
                .main2(foodMenu.getMain2())
                .side1(foodMenu.getSide1())
                .side2(foodMenu.getSide2())
                .side3(foodMenu.getSide3())
                .calories(foodMenu.getNutritionFact().getCalories())
                .carbohydrate(foodMenu.getNutritionFact().getCarbohydrate())
                .protein(foodMenu.getNutritionFact().getProtein())
                .fat(foodMenu.getNutritionFact().getFat())
                .sugar(foodMenu.getNutritionFact().getSugar())
                .sodium(foodMenu.getNutritionFact().getSodium())
                .build();
    }

    public List<FoodMenuDTO> toFoodMenuDTOS(List<FoodMenu> foodMenus) {
        List<FoodMenuDTO> foodMenuDTOS = new ArrayList<>();
        for (FoodMenu foodMenu : foodMenus) {
            foodMenuDTOS.add(toFoodMenuDTO(foodMenu));
        }
        return foodMenuDTOS;
    }

    public FoodMenu toFoodMenuEntity(String input) {
        Pattern namePattern = Pattern.compile("식단 이름: (.+)");
        Pattern pricePattern = Pattern.compile("가격: (.+)");
        Pattern main1Pattern = Pattern.compile("Main1: (.+)");
        Pattern main2Pattern = Pattern.compile("Main2: (.+)");
        Pattern side1Pattern = Pattern.compile("Side1: (.+)");
        Pattern side2Pattern = Pattern.compile("Side2: (.+)");
        Pattern side3Pattern = Pattern.compile("Side3: (.+)");
        Pattern caloriesPattern = Pattern.compile("칼로리: (.+)kcal");
        Pattern carbohydratePattern = Pattern.compile("탄수화물: (.+)g");
        Pattern proteinPattern = Pattern.compile("단백질: (.+)g");
        Pattern fatPattern = Pattern.compile("지방: (.+)g");
        Pattern sugarPattern = Pattern.compile("당류: (.+)g");
        Pattern sodiumPattern = Pattern.compile("나트륨: (.+)mg");

        NutritionFact nutritionFact = NutritionFact.builder()
                .calories(getMatchedValue(caloriesPattern, input) + "kcal")
                .carbohydrate(getMatchedValue(carbohydratePattern, input) + "g")
                .protein(getMatchedValue(proteinPattern, input) + "g")
                .fat(getMatchedValue(fatPattern, input) + "g")
                .sugar(getMatchedValue(sugarPattern, input) + "g")
                .sodium(getMatchedValue(sodiumPattern, input) + "mg")
                .build();

        FoodMenu foodMenu = FoodMenu.builder()
                .name(getMatchedValue(namePattern, input))
                .image(createMealImage(extractMainAndSideMenus(input)))
                .price(getMatchedValue(pricePattern, input))
                .main1(getMatchedValue(main1Pattern, input))
                .main2(getMatchedValue(main2Pattern, input))
                .side1(getMatchedValue(side1Pattern, input))
                .side2(getMatchedValue(side2Pattern, input))
                .side3(getMatchedValue(side3Pattern, input))
                .nutritionFact(nutritionFact)
                .build();

        return foodMenu;
    }

    /**
     * AI 서비스를 통해 식단 이미지 생성
     */
    private String createMealImage(String menuDescription) {
        return aiService.generateFoodImage(menuDescription);
    }

    public WeeklyMealPlanDTO toWeeklyMealPlanDTO(WeeklyMealPlan weeklyMealPlan) {
        return WeeklyMealPlanDTO.builder()
                .dailyMealPlans(toDailyMealPlansDTOS(weeklyMealPlan.getDailyMealPlans()))
                .startDate(weeklyMealPlan.getStartDate())
                .endDate(weeklyMealPlan.getEndDate())
                .build();
    }

    public List<DailyMealPlanDTO> toDailyMealPlansDTOS(List<DailyMealPlan> dailyMealPlans) {
        List<DailyMealPlanDTO> dailyMealPlansDTOS = new ArrayList<>();
        for (DailyMealPlan dailyMealPlan : dailyMealPlans) {
            dailyMealPlansDTOS.add(toDailyMealPlanDTO(dailyMealPlan));
        }
        return dailyMealPlansDTOS;
    }

    public DailyMealPlanDTO toDailyMealPlanDTO(DailyMealPlan dailyMealPlan) {
        return DailyMealPlanDTO.builder()
                .day(dailyMealPlan.getDay())
                .mealOptions(toMealOptionDTOS(dailyMealPlan.getMealOptions()))
                .build();
    }

    public List<MealOptionDTO> toMealOptionDTOS(List<MealOption> mealOptions) {
        List<MealOptionDTO> mealOptionDTOS = new ArrayList<>();
        for (MealOption mealOption : mealOptions) {
            mealOptionDTOS.add(toMealOptionDTO(mealOption));
        }
        return mealOptionDTOS;
    }

    public MealOptionDTO toMealOptionDTO(MealOption mealOption) {
        return MealOptionDTO.builder()
                .foodMenus(toFoodMenuDTOS(mealOption.getFoodMenus()))
                .mealType(mealOption.getMealType())
                .build();
    }

    public String extractMainAndSideMenus(String input) {
        StringBuilder result = new StringBuilder();

        // 정규 표현식 패턴: Main1, Main2, Side1, Side2, Side3 항목과 괄호 안의 내용
        Pattern pattern = Pattern.compile("(Main\\d+:|Side\\d+:)\\s*([^\\(]+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            // 메뉴 항목 추출
            String menuItem = matcher.group(1) + " " + matcher.group(2).trim();
            result.append(menuItem).append(" ");
        }

        // 결과 문자열 반환
        return result.toString().trim();
    }

    private String getMatchedValue(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}