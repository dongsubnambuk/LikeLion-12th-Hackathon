package Service;

import DAO.DietNotificationDAO;
import DTO.DietNotificationDTO;
import DTO.ExternalDietNotificationDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietNotificationServiceImpl implements DietNotificationService {

    private final DietNotificationDAO dietNotificationDAO;
    private final NotificationSender notificationSender;

    public DietNotificationServiceImpl(DietNotificationDAO dietNotificationDAO, NotificationSender notificationSender) {
        this.dietNotificationDAO = dietNotificationDAO;
        this.notificationSender = notificationSender;
    }

    @Override
    public DietNotificationDTO createDietNotification(DietNotificationDTO dietNotificationDTO) {
        return dietNotificationDAO.create(dietNotificationDTO);
    }

    @Override
    public DietNotificationDTO getDietNotificationById(Long id) {
        return dietNotificationDAO.findById(id);
    }

    @Override
    public List<DietNotificationDTO> getDietNotificationsByUserId(Long userId) {
        return dietNotificationDAO.findByUserId(userId);
    }

    @Override
    public DietNotificationDTO updateDietNotification(DietNotificationDTO dietNotificationDTO) {
        return dietNotificationDAO.update(dietNotificationDTO);
    }

    @Override
    public void deleteDietNotification(Long id) {
        dietNotificationDAO.delete(id);
    }

}
