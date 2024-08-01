package com.example.ai.Service;

import com.example.ai.DAO.DietDao;
import com.example.ai.DTO.*;
import com.example.ai.Entity.FoodMenu;
import com.example.ai.Entity.NutritionFacts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DietServiceImpl implements DietService{
    private final DietDao dietDao;
    private final RestTemplate template;
    private final Logger logger = LoggerFactory.getLogger(DietServiceImpl.class);

    @Value("${openai.chatModel}")
    private String chatModel;

    @Value("${openai.api.chatUrl}")
    private String chatApiURL;

    @Value("${openai.api.imageUrl}")
    private String imageApiURL;

    @Value("${openai.imageModel}")
    private String imageModel;

    @Autowired
    public DietServiceImpl(DietDao dietDao, RestTemplate template) {
        this.dietDao = dietDao;
        this.template = template;
    }

    @Override
    public FoodMenuDTO createDiet(String price){
        String prompt = "가격: " + price + "원\n이 가격에 맞는 영양 있는 식단을 구성해줘. 식단은 MainMenu 2개와 SideMenu 3개로 구성되어야 해. 식단의 이름도 정해줘.";
        ChatRequest request = new ChatRequest(chatModel, prompt);
        ChatResponse chatResponse =  template.postForObject(chatApiURL, request, ChatResponse.class);

        // 응답 검토
        if (chatResponse == null || chatResponse.getChoices().isEmpty()) {
            throw new IllegalStateException("API 응답이 유효하지 않습니다.");
        }

        String answer = chatResponse.getChoices().get(0).getMessage().getContent();
        if (answer == null || answer.isEmpty()) {
            throw new IllegalStateException("API 응답 내용이 비어 있습니다.");
        }

        // 문자열을 엔티티로 변환
        FoodMenu foodMenu = toEntity(answer);
        if (foodMenu == null) {
            throw new IllegalStateException("응답을 FoodMenu 엔티티로 변환하는 데 실패했습니다.");
        }

        dietDao.createDiet(foodMenu);

        return toFoodMenuDTO(foodMenu);
    }

    public String createDietImage(String foodMenu){
        String prompt = foodMenu + "다음 식단 메뉴 5가지로만 구성된 이미지를 생성해줘, 다른 부가적인 요소들은 빼고 테이블과 메뉴 5가지만 보여줘";
        logger.info(prompt);
        ImageRequest imageRequest = new ImageRequest(imageModel, prompt, "b64_json");
        ImageResponse imageResponse = template.postForObject(imageApiURL, imageRequest, ImageResponse.class);
        String b64_image = imageResponse.getData().get(0).getB64_json();
        byte[] decodedBytes = Base64.getDecoder().decode(b64_image);
        String image = new String(decodedBytes);
        return image;
    }

    public FoodMenuDTO toFoodMenuDTO(FoodMenu foodMenu){
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
                .calories(foodMenu.getNutritionFacts().getCalories())
                .carbohydrate(foodMenu.getNutritionFacts().getCarbohydrate())
                .protein(foodMenu.getNutritionFacts().getProtein())
                .fat(foodMenu.getNutritionFacts().getFat())
                .sugar(foodMenu.getNutritionFacts().getSugar())
                .sodium(foodMenu.getNutritionFacts().getSodium())
                .build();
    }

    public FoodMenu toEntity(String input) {

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


        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .calories(getMatchedValue(caloriesPattern, input) + "kcal")
                .carbohydrate(getMatchedValue(carbohydratePattern, input) + "g")
                .protein(getMatchedValue(proteinPattern, input) + "g")
                .fat(getMatchedValue(fatPattern, input) + "g")
                .sugar(getMatchedValue(sugarPattern, input) + "g")
                .sodium(getMatchedValue(sodiumPattern, input) + "mg")
                .build();

        FoodMenu foodMenu = FoodMenu.builder()
                .name(getMatchedValue(namePattern, input))
                .image(createDietImage(extractMainAndSideMenus(input)))
                .price(getMatchedValue(pricePattern, input))
                .main1(getMatchedValue(main1Pattern, input))
                .main2(getMatchedValue(main2Pattern, input))
                .side1(getMatchedValue(side1Pattern, input))
                .side2(getMatchedValue(side2Pattern, input))
                .side3(getMatchedValue(side3Pattern, input))
                .nutritionFacts(nutritionFacts)
                .build();

        return foodMenu;
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
