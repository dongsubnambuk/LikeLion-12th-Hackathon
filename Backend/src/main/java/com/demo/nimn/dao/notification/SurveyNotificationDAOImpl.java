package com.demo.nimn.dao.notification;

import com.example.notificationserver.DTO.ReviewDTO;
import com.example.notificationserver.DTO.SurveyNotificationDTO;
import com.example.notificationserver.Entity.ReviewEntity;
import com.example.notificationserver.Entity.SurveyNotificationEntity;
import com.example.notificationserver.Repository.SurveyNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
public class SurveyNotificationDAOImpl implements SurveyNotificationDAO {
    private final SurveyNotificationRepository surveyNotificationRepository;

    @Autowired
    public SurveyNotificationDAOImpl(SurveyNotificationRepository surveyNotificationRepository) {
        this.surveyNotificationRepository = surveyNotificationRepository;
    }

    // Entity to DTO
    private SurveyNotificationDTO surveyNotificationEntityToDTO(SurveyNotificationEntity surveyNotificationEntity) {
        return SurveyNotificationDTO.builder()
                .email(surveyNotificationEntity.getEmail())
                .notificationContent(surveyNotificationEntity.getNotificationContent())
                .notificationTime(surveyNotificationEntity.getNotificationTime())
                .dailyReviewId(surveyNotificationEntity.getDailyReviewId())
                .reviewDate(surveyNotificationEntity.getReviewDate())
                .reviews(surveyNotificationEntity.getReviews() != null ?
                        surveyNotificationEntity.getReviews().stream().map(this::reviewEntityToDTO).collect(Collectors.toList()) :
                        new ArrayList<>())
                .build();
    }


    // DTO to Entity
    private SurveyNotificationEntity DTOtoSurveyNotificationEntity(SurveyNotificationDTO surveyNotificationDTO) {
        return SurveyNotificationEntity.builder()
                .email(surveyNotificationDTO.getEmail())
                .notificationContent(surveyNotificationDTO.getNotificationContent())
                .notificationTime(surveyNotificationDTO.getNotificationTime())
                .dailyReviewId(surveyNotificationDTO.getDailyReviewId())
                .reviewDate(surveyNotificationDTO.getReviewDate())
                .reviews(surveyNotificationDTO.getReviews() != null ? surveyNotificationDTO.getReviews().stream().map(this::reviewDTOToEntity).collect(Collectors.toList()) : null)
                .build();
    }


    // Review Entity to DTO
    private ReviewDTO reviewEntityToDTO(ReviewEntity reviewEntity) {
        return ReviewDTO.builder()
                .reviewId(reviewEntity.getReviewId())
                .foodImage(reviewEntity.getFoodImage())
                .foodName(reviewEntity.getFoodName())
                .likes(reviewEntity.getLikes())
                .disLikes(reviewEntity.getDisLikes())
                .comments(reviewEntity.getComments())
                .build();
    }

    // Review DTO to Entity
    private ReviewEntity reviewDTOToEntity(ReviewDTO reviewDTO) {
        return ReviewEntity.builder()
                .reviewId(reviewDTO.getReviewId())
                .foodImage(reviewDTO.getFoodImage())
                .foodName(reviewDTO.getFoodName())
                .likes(reviewDTO.getLikes())
                .disLikes(reviewDTO.getDisLikes())
                .comments(reviewDTO.getComments())
                .build();
    }

    @Override
    public SurveyNotificationDTO create(SurveyNotificationDTO surveyNotificationDTO) {
        SurveyNotificationEntity notificationEntity = DTOtoSurveyNotificationEntity(surveyNotificationDTO);
        return surveyNotificationEntityToDTO(surveyNotificationRepository.save(notificationEntity));
    }

    @Override
    public SurveyNotificationDTO update(SurveyNotificationDTO surveyNotificationDTO) {
        SurveyNotificationEntity notificationEntity = DTOtoSurveyNotificationEntity(surveyNotificationDTO);
        return surveyNotificationEntityToDTO(surveyNotificationRepository.save(notificationEntity));
    }

    @Override
    public void delete(SurveyNotificationDTO surveyNotificationDTO) {
        SurveyNotificationEntity notificationEntity = DTOtoSurveyNotificationEntity(surveyNotificationDTO);
        surveyNotificationRepository.delete(notificationEntity);
    }
}
