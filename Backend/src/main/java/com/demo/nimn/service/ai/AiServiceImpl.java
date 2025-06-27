package com.demo.nimn.service.ai;

import com.demo.nimn.dto.chatgpt.*;
import com.demo.nimn.service.image.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Objects;

@Slf4j
@Service
public class AiServiceImpl implements AiService {

    private final RestTemplate restTemplate;
    private final ImageService imageService;
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
    public AiServiceImpl(@Qualifier("openAiRestTemplate") RestTemplate restTemplate,
                         ImageService imageService) {
        this.restTemplate = restTemplate;
        this.imageService = imageService;
    }

    @Override
    public String generateFood(String price) {
        String prompt = buildMealPlanPrompt(price);

        ChatRequest request = new ChatRequest(chatModel, prompt);
        ChatResponse chatResponse = restTemplate.postForObject(chatApiURL, request, ChatResponse.class);
        log.info(Objects.requireNonNull(chatResponse).toString());

        validateChatResponse(chatResponse);

        return chatResponse.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public String generateFoodImage(String foodDescription) {
        String prompt = buildImagePrompt(foodDescription);
        logger.info("Image generation prompt: {}", prompt);

        ImageRequest imageRequest = new ImageRequest(imageModel, prompt, "b64_json");
        ImageResponse imageResponse = restTemplate.postForObject(imageApiURL, imageRequest, ImageResponse.class);

        validateImageResponse(imageResponse);

        String b64_image = imageResponse.getData().get(0).getB64_json();
        byte[] decodedBytes = Base64.getDecoder().decode(b64_image);

        return imageService.uploadByteImage(decodedBytes);
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

        String url = imageResponse.getData().get(0).getB64_json();
        if (url == null || url.isEmpty()) {
            throw new IllegalStateException("생성된 이미지 데이터가 비어 있습니다.");
        }
    }
}