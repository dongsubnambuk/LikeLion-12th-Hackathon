package com.demo.nimn.service.notification;

import com.example.notificationserver.DAO.SurveyNotificationDAO;
import com.example.notificationserver.DTO.SurveyNotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyNotificationServiceImpl implements SurveyNotificationService {
    private final SurveyNotificationDAO surveyNotificationDAO;

    @Autowired
    public SurveyNotificationServiceImpl(SurveyNotificationDAO surveyNotificationDAO) {
        this.surveyNotificationDAO = surveyNotificationDAO;
    }

    @Override
    public SurveyNotificationDTO createSurveyNotification(SurveyNotificationDTO surveyNotificationDTO) {
        return surveyNotificationDAO.create(surveyNotificationDTO);
    }

    @Override
    public SurveyNotificationDTO updateSurveyNotification(SurveyNotificationDTO surveyNotificationDTO) {
        return surveyNotificationDAO.update(surveyNotificationDTO);
    }

    @Override
    public SurveyNotificationDTO deleteSurveyNotification(SurveyNotificationDTO surveyNotificationDTO) {
        return null;
    }
}
