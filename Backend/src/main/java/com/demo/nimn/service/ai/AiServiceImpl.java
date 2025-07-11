package com.demo.nimn.service.ai;

import com.demo.nimn.dto.chatgpt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiServiceImpl implements AiService {

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(AiServiceImpl.class);

    @Value("${openai.chatModel}")
    private String chatModel;

    @Value("${openai.api.chatUrl}")
    private String chatApiURL;

    @Value("${openai.api.imageUrl}")
    private String imageApiURL;

    @Value("${openai.imageModel}")
    private String imageModel;

    @Autowired
    public AiServiceImpl(@Qualifier("openAiRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String generateFood(String price) {
        String prompt = buildMealPlanPrompt(price);

        ChatRequest request = new ChatRequest(chatModel, prompt);
        ChatResponse chatResponse = restTemplate.postForObject(chatApiURL, request, ChatResponse.class);

        validateChatResponse(chatResponse);

        return chatResponse.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public String generateFoodImage(String foodDescription) {
        String prompt = buildImagePrompt(foodDescription);
        logger.info("Image generation prompt: {}", prompt);

//        ImageRequest imageRequest = new ImageRequest(imageModel, prompt, "b64_json");
//        ImageResponse imageResponse = template.postForObject(imageApiURL, imageRequest, ImageResponse.class);
//        String b64_image = imageResponse.getData().get(0).getB64_json();
//        byte[] decodedBytes = Base64.getDecoder().decode(b64_image);

        // TODO-jh: 이미지 생성 반환 format b64_json으로 변경해서 저장 필요. open ai에서 생성한 url은 1시간 후 자동 삭제 됨.
        ImageRequest imageRequest = new ImageRequest(imageModel, prompt, "url");
        ImageResponse imageResponse = restTemplate.postForObject(imageApiURL, imageRequest, ImageResponse.class);

        validateImageResponse(imageResponse);

        return imageResponse.getData().get(0).getUrl();
    }

    private String buildMealPlanPrompt(String price) {
        return "가격: " + price + "원\n이 가격에 맞는 영양 있는 식단을 구성해줘. " +
                "식단은 MainMenu 2개와 SideMenu 3개로 구성되어야 해. 식단의 이름도 정해줘.";
    }

    private String buildImagePrompt(String menuDescription) {
        return menuDescription + "\n다음 식단 메뉴 5가지로만 구성된 이미지를 생성해줘, " +
                "다른 부가적인 요소들은 빼고 테이블과 메뉴 5가지만 보여줘";
    }

    private void validateChatResponse(ChatResponse chatResponse) {
        if (chatResponse == null || chatResponse.getChoices().isEmpty()) {
            throw new IllegalStateException("ChatGPT API 응답이 유효하지 않습니다.");
        }

        String content = chatResponse.getChoices().get(0).getMessage().getContent();
        if (content == null || content.isEmpty()) {
            throw new IllegalStateException("ChatGPT API 응답 내용이 비어 있습니다.");
        }
    }

    private void validateImageResponse(ImageResponse imageResponse) {
        if (imageResponse == null || imageResponse.getData().isEmpty()) {
            throw new IllegalStateException("이미지 생성 API 응답이 유효하지 않습니다.");
        }

        String url = imageResponse.getData().get(0).getUrl();
        if (url == null || url.isEmpty()) {
            throw new IllegalStateException("생성된 이미지 URL이 비어 있습니다.");
        }
    }
}