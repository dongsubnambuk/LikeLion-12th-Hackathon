package com.demo.nimn.entity.notification;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diet_notification")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DietNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column
    private String notificationContent;
    @Column
    private LocalDateTime notificationTime;

    @Builder
    public DietNotificationEntity(String email, String notificationContent, LocalDateTime notificationTime){
        this.email = email;
        this.notificationContent = notificationContent;
        this.notificationTime = notificationTime;
    }

}
