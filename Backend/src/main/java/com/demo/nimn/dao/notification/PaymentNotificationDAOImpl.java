package com.demo.nimn.dao.notification;

import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.Entity.PaymentNotificationEntity;
import com.example.notificationserver.Repository.PaymentNotificationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PaymentNotificationDAOImpl implements PaymentNotificationDAO {
    private final PaymentNotificationRepository paymentNotificationRepository;

    public PaymentNotificationDAOImpl(PaymentNotificationRepository paymentNotificationRepository) {
        this.paymentNotificationRepository = paymentNotificationRepository;
    }

    // Entity to DTO
    private PaymentNotificationDTO paymentNotificationEntityToDTO(PaymentNotificationEntity paymentNotificationEntity) {
        return PaymentNotificationDTO.builder()
                .id(paymentNotificationEntity.getId())
                .email(paymentNotificationEntity.getEmail())
                .notificationContent(paymentNotificationEntity.getNotificationContent())
                .notificationTime(paymentNotificationEntity.getNotificationTime())
                .build();
    }

    // DTO to Entity
    private PaymentNotificationEntity paymentNotificationDTOToEntity(PaymentNotificationDTO paymentNotificationDTO) {
        return PaymentNotificationEntity.builder()
                .id(paymentNotificationDTO.getId())
                .email(paymentNotificationDTO.getEmail())
                .notificationContent(paymentNotificationDTO.getNotificationContent())
                .notificationTime(paymentNotificationDTO.getNotificationTime())
                .build();
    }

    @Override
    public PaymentNotificationDTO create(PaymentNotificationDTO paymentNotificationDTO) {
        PaymentNotificationEntity paymentNotificationEntity = paymentNotificationDTOToEntity(paymentNotificationDTO);
        PaymentNotificationEntity savedEntity = paymentNotificationRepository.save(paymentNotificationEntity);
        return paymentNotificationEntityToDTO(savedEntity);
    }

    @Override
    public void updateLastPaymentDate(PaymentNotificationDTO paymentNotificationDTO, LocalDateTime newPaymentDate) {
        Optional<PaymentNotificationEntity> entityOpt = paymentNotificationRepository.findById(paymentNotificationDTO.getId());
        if (entityOpt.isPresent()) {
            PaymentNotificationEntity paymentNotificationEntity = entityOpt.get();
            paymentNotificationRepository.save(paymentNotificationEntity);
        }
    }

    @Override
    public void updateNotificationContentAndTime(PaymentNotificationDTO paymentNotificationDTO) {
        Optional<PaymentNotificationEntity> entityOpt = paymentNotificationRepository.findById(paymentNotificationDTO.getId());
        if (entityOpt.isPresent()) {
            PaymentNotificationEntity paymentNotificationEntity = entityOpt.get();
            paymentNotificationEntity.setNotificationContent(paymentNotificationDTO.getNotificationContent());
            paymentNotificationEntity.setNotificationTime(paymentNotificationDTO.getNotificationTime());
            paymentNotificationRepository.save(paymentNotificationEntity);
        }
    }

    @Override
    public PaymentNotificationDTO update(PaymentNotificationDTO paymentNotificationDTO) {
        Optional<PaymentNotificationEntity> entityOpt = paymentNotificationRepository.findById(paymentNotificationDTO.getId());
        if (entityOpt.isPresent()) {
            PaymentNotificationEntity paymentNotificationEntity = entityOpt.get();
            paymentNotificationEntity.setEmail(paymentNotificationDTO.getEmail());
            paymentNotificationEntity.setNotificationContent(paymentNotificationDTO.getNotificationContent());
            paymentNotificationEntity.setNotificationTime(paymentNotificationDTO.getNotificationTime());
            PaymentNotificationEntity updatedEntity = paymentNotificationRepository.save(paymentNotificationEntity);
            return paymentNotificationEntityToDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        paymentNotificationRepository.deleteById(id);
    }
}
