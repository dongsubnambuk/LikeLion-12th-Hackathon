package com.demo.nimn.service.notification;

import com.example.notificationserver.DTO.*;
import com.example.notificationserver.Entity.DailyReviewEntity;
import com.example.notificationserver.Entity.ExternalDietInfoEntity;
import com.example.notificationserver.Entity.ExternalPaymentInfoEntity;
import com.example.notificationserver.Entity.ReviewEntity;
import com.example.notificationserver.Repository.DailyReviewRepository;
import com.example.notificationserver.Repository.ExternalDietInfoRepository;
import com.example.notificationserver.Repository.ExternalPaymentInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final ExternalPaymentInfoRepository externalPaymentInfoRepository;
    private final ExternalDietInfoRepository externalDietInfoRepository;
    private final DailyReviewRepository dailyReviewRepository;
    private final NotificationService notificationService;
    private final SurveyNotificationService surveyNotificationService;

    @Autowired
    public CommunicationServiceImpl(DiscoveryClient discoveryClient, RestTemplate restTemplate, ExternalPaymentInfoRepository externalPaymentInfoRepository, ExternalDietInfoRepository externalDietInfoRepository, DailyReviewRepository dailyReviewRepository, NotificationService notificationService, SurveyNotificationService surveyNotificationService) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
        this.externalPaymentInfoRepository = externalPaymentInfoRepository;
        this.externalDietInfoRepository = externalDietInfoRepository;
        this.dailyReviewRepository = dailyReviewRepository;
        this.notificationService = notificationService;
        this.surveyNotificationService = surveyNotificationService;
    }

    @Override
    public String fetchAndSavePaymentInfo(String email) throws URISyntaxException {
        return null;
    }

    @Override
    public ExternalPaymentNotificationDTO getUserEmails() {
        List<ServiceInstance> instances = discoveryClient.getInstances("PAYMENT-SERVER");
        if (instances == null || instances.isEmpty()) {
            throw new IllegalStateException("No PAYMENT-SERVER instances available");
        }
        ServiceInstance paymentService = instances.get(0);
        URI uri = URI.create(paymentService.getUri() + "/api/payment/unpaid-users");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ExternalPaymentApiResponseDTO> response;

        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ExternalPaymentApiResponseDTO.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send request to PAYMENT-SERVER", e);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            ExternalPaymentApiResponseDTO userDTO = response.getBody();
            if (userDTO != null && userDTO.getEmail() != null) {
                System.out.println("Received response from PAYMENT-SERVER: " + userDTO.getEmail());
                return new ExternalPaymentNotificationDTO(userDTO.getEmail(), false, LocalDate.now());
            }
        } else {
            throw new IllegalStateException("Failed to fetch emails from PAYMENT-SERVER");
        }
        return null;
    }

    @Override
    public void saveToExternalPaymentInfoEntity(ExternalPaymentNotificationDTO dto) {
        List<String> emails = dto.getEmails();
        for (String email : emails) {
            ExternalPaymentInfoEntity entity = ExternalPaymentInfoEntity.builder()
                    .email(email)
                    .processed(dto.isProcessed())
                    .date(dto.getDate())
                    .build();
            externalPaymentInfoRepository.save(entity);
        }
    }

    @Override
    public void getUserDietEmails(String date) {
        List<ServiceInstance> instances = discoveryClient.getInstances("USERMEAL-SERVER");
        if (instances == null || instances.isEmpty()) {
            throw new IllegalStateException("No USERMEAL-SERVER instances available");
        }
        ServiceInstance userMealService = instances.get(0);
        URI uri = URI.create(userMealService.getUri() + "/api/userMeal/daily/read/" + date);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DailyDietDTO[]> response;

        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, DailyDietDTO[].class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send request to USERMEAL-SERVER", e);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            DailyDietDTO[] dailyDiets = response.getBody();
            if (dailyDiets != null) {
                for (DailyDietDTO dailyDiet : dailyDiets) {
                    System.out.println("Received response from USERMEAL-SERVER: " + dailyDiet);
                    String breakfast = null;
                    String lunch = null;
                    String dinner = null;

                    if (dailyDiet.getMealSelections() != null) {
                        for (MealSelectionDTO mealSelection : dailyDiet.getMealSelections()) {
                            if ("아침".equals(mealSelection.getMealTime())) {
                                breakfast = mealSelection.getFoodMenu().getName();
                            } else if ("점심".equals(mealSelection.getMealTime())) {
                                lunch = mealSelection.getFoodMenu().getName();
                            } else if ("저녁".equals(mealSelection.getMealTime())) {
                                dinner = mealSelection.getFoodMenu().getName();
                            }
                        }
                    }

                    ExternalDietInfoEntity entity = ExternalDietInfoEntity.builder()
                            .email(dailyDiet.getUserEmail())
                            .breakfast(breakfast)
                            .lunch(lunch)
                            .dinner(dinner)
                            .processedBreakfast(false)
                            .processedLunch(false)
                            .processedDinner(false)
                            .date(dailyDiet.getDate())
                            .build();
                    externalDietInfoRepository.save(entity);
                }
            }
        } else {
            throw new IllegalStateException("Failed to fetch data from USERMEAL-SERVER");
        }
    }


    @Override
    @Transactional
    public void fetchAndSaveDailyReviews() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
        String formattedDate = date.format(formatter);
        List<ServiceInstance> instances = discoveryClient.getInstances("MEAL-SERVER");
        if (instances == null || instances.isEmpty()) {
            throw new IllegalStateException("No MEAL-SERVER instances available");
        }
        ServiceInstance mealService = instances.get(0);
        URI uri = URI.create(mealService.getUri() + "/api/meal/review/" + date);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<DailyReviewDTO[]> response;
        System.out.println("Request URI: " + uri);

        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, request, DailyReviewDTO[].class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send request to MEAL-SERVER", e);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            DailyReviewDTO[] dailyReviewDTOs = response.getBody();
            if (dailyReviewDTOs != null) {
                Map<String, List<DailyReviewDTO>> groupedReviews = groupReviewsByUserEmail(dailyReviewDTOs);
                saveGroupedReviews(groupedReviews);

                // 각 이메일별로 데이터를 전송하고 DailyReviewEntity를 삭제합니다.
                for (String userEmail : groupedReviews.keySet()) {
                    List<DailyReviewDTO> userReviews = groupedReviews.get(userEmail);
                    for (DailyReviewDTO reviewDTO : userReviews) {
                        SurveyNotificationDTO notification = SurveyNotificationDTO.builder()
                                .email(userEmail)
                                .notificationContent(formattedDate + " 식단 만족도 조사에 참여해주세요~")
                                .reviewDate(reviewDTO.getReviewDate())  // reviewDate 설정
                                .dailyReviewId(reviewDTO.getDailyReviewId())  // dailyReviewId 설정
                                .notificationTime(LocalDateTime.now())  // notificationTime 설정
                                .reviews(reviewDTO.getReviews())  // reviews 설정
                                .build();
                        surveyNotificationService.createSurveyNotification(notification);
                        notificationService.sendSurveyNotification(notification);
                    }
                    deleteDailyReviewByEmail(userEmail);
                }
            } else {
                throw new IllegalStateException("No reviews found for the given date");
            }
        } else {
            throw new IllegalStateException("Failed to fetch reviews from MEAL-SERVER");
        }
    }

    private Map<String, List<DailyReviewDTO>> groupReviewsByUserEmail(DailyReviewDTO[] dailyReviewDTOs) {
        return Arrays.stream(dailyReviewDTOs)
                .collect(Collectors.groupingBy(DailyReviewDTO::getUserEmail));
    }

    private void saveGroupedReviews(Map<String, List<DailyReviewDTO>> groupedReviews) {
        for (Map.Entry<String, List<DailyReviewDTO>> entry : groupedReviews.entrySet()) {
            String userEmail = entry.getKey();
            List<DailyReviewDTO> reviews = entry.getValue();

            DailyReviewEntity dailyReviewEntity = DailyReviewEntity.builder()
                    .userEmail(userEmail)
                    .reviewDate(reviews.get(0).getReviewDate())
                    .reviewEntities(reviews.stream().flatMap(dto -> dto.getReviews().stream())
                            .map(this::convertToReviewEntity)
                            .collect(Collectors.toList()))
                    .build();

            dailyReviewRepository.save(dailyReviewEntity);
        }
    }

    private ReviewEntity convertToReviewEntity(ReviewDTO reviewDTO) {
        return ReviewEntity.builder()
                .foodImage(reviewDTO.getFoodImage())
                .foodName(reviewDTO.getFoodName())
                .likes(reviewDTO.getLikes())
                .disLikes(reviewDTO.getDisLikes())
                .comments(reviewDTO.getComments())
                .build();
    }
    @Transactional
    public void deleteDailyReviewByEmail(String userEmail) {
        dailyReviewRepository.deleteByUserEmail(userEmail);
    }
}
