package com.demo.nimn.service.food;

import com.example.foodserver.DTO.Response.FoodMenuDTO;
import com.example.foodserver.DTO.Request.UserDailyMealPlanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunicationServiceImpl implements CommunicationService{
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    @Autowired
    public CommunicationServiceImpl(DiscoveryClient discoveryClient,
                                    RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @Override
    public String createReview(String userEmail, List<UserDailyMealPlanDTO> userDailyMealPlanDTOS){
        try {
            ServiceInstance mealService = discoveryClient.getInstances("MEAL-SERVER").get(0);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("userEmail", userEmail);
            bodyMap.put("userDailyMealPlans", userDailyMealPlanDTOS);
            HttpEntity<Map<String, Object>> http = new HttpEntity<>(bodyMap, headers);
            URI uri = new URI(mealService.getUri() + "/api/meal/review");
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, http, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to fetch meal");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch meal", e);
        }
    }

    @Override
    public FoodMenuDTO getFoodMenu(Long foodMenuId){
        try {
            ServiceInstance mealService = discoveryClient.getInstances("MEAL-SERVER").get(0);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> http = new HttpEntity<>(headers);
            URI uri = new URI(mealService.getUri() + "/api/meal/food/" + foodMenuId);
            ResponseEntity<FoodMenuDTO> response = restTemplate.exchange(uri, HttpMethod.GET, http, FoodMenuDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to fetch meal");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch meal", e);
        }
    }
}