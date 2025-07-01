package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.*;
import com.demo.nimn.entity.food.*;
import com.demo.nimn.entity.review.ReviewSummary;
import com.demo.nimn.repository.food.FoodRepository;
import com.demo.nimn.repository.review.ReviewSummaryRepository;
import com.demo.nimn.service.ai.AiService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final AiService aiService;
    private final ReviewSummaryRepository reviewSummaryRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository,
                           AiService aiService,
                           ReviewSummaryRepository reviewSummaryRepository) {
        this.foodRepository = foodRepository;
        this.aiService = aiService;
        this.reviewSummaryRepository = reviewSummaryRepository;
    }

    @Transactional
    @Override
    public FoodDTO createFood(Long price) {
        String foodPlanText = aiService.generateFood(price.toString());

        Food food = convertToFood(foodPlanText);

        if (food == null) {
            throw new IllegalStateException("응답을 Food 엔티티로 변환하는 데 실패했습니다.");
        }

        Food savedFood = foodRepository.save(food);

        createInitialReviewSummary(savedFood);

        return convertToFoodDTO(savedFood);
    }

    private void createInitialReviewSummary(Food food) {
        ReviewSummary reviewSummary = ReviewSummary.builder()
                .food(food)
                .reviews(new ArrayList<>())
                .build();

        reviewSummaryRepository.save(reviewSummary);

        food.setReviewSummary(reviewSummary);
    }

    @Override
    public FoodDTO readFoodDTOByFoodId(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalStateException("Invalid Food Id: " + foodId));
        return convertToFoodDTO(food);
    }

    @Override
    public List<FoodDTO> readAll() {
        List<Food> foods = foodRepository.findAll();
        return toFoodDTOS(foods);
    }

    public List<FoodDTO> toFoodDTOS(List<Food> foods) {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        for (Food food : foods) {
            foodDTOS.add(convertToFoodDTO(food));
        }
        return foodDTOS;
    }

    public Food convertToFood(String input) {
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

        return Food.builder()
                .name(getMatchedValue(namePattern, input))
                .image(createFoodImage(extractMainAndSideMenus(input)))
                .price(Long.parseLong(Objects.requireNonNull(getMatchedValue(pricePattern, input))
                        .replaceAll("원$", "")))
                .main1(getMatchedValue(main1Pattern, input))
                .main2(getMatchedValue(main2Pattern, input))
                .side1(getMatchedValue(side1Pattern, input))
                .side2(getMatchedValue(side2Pattern, input))
                .side3(getMatchedValue(side3Pattern, input))
                .nutritionFact(nutritionFact)
                .build();
    }

    // AI 서비스를 통해 식단 이미지 생성
    private String createFoodImage(String menuDescription) {
        return aiService.generateFoodImage(menuDescription);
    }

    public String extractMainAndSideMenus(String input) {
        StringBuilder result = new StringBuilder();

        // 정규 표현식 패턴: Main1, Main2, Side1, Side2, Side3 항목과 괄호 안의 내용
        Pattern pattern = Pattern.compile("(Main\\d+:|Side\\d+:)\\s*([^\\(]+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            // 메뉴 항목 추출
            String foodItem = matcher.group(1) + " " + matcher.group(2).trim();
            result.append(foodItem).append(" ");
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

    @Override
    public FoodDTO convertToFoodDTO(Food food) {
        return FoodDTO.builder()
                .id(food.getId())
                .name(food.getName())
                .image(food.getImage())
                .price(food.getPrice())
                .main1(food.getMain1())
                .main2(food.getMain2())
                .side1(food.getSide1())
                .side2(food.getSide2())
                .side3(food.getSide3())
                .calories(food.getNutritionFact().getCalories())
                .carbohydrate(food.getNutritionFact().getCarbohydrate())
                .protein(food.getNutritionFact().getProtein())
                .fat(food.getNutritionFact().getFat())
                .sugar(food.getNutritionFact().getSugar())
                .sodium(food.getNutritionFact().getSodium())
                .build();
    }
}