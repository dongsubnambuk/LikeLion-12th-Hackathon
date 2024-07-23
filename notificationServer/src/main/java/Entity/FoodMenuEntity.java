package Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notification")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FoodMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private String notificationContent;
    @Column
    private LocalDateTime notificationTime;

    @Builder
    public FoodMenuEntity(Long userId, String notificationContent, LocalDateTime notificationTime){
        this.userId = userId;
        this.notificationContent = notificationContent;
        this.notificationTime = notificationTime;
    }


    public void updateUserId(Long userId){
        this.userId = userId;
    }

    public void updateNotificationContent(String notificationContent){
        this.notificationContent = notificationContent;
    }

    public void updateNotificationTime(LocalDateTime notificationTime){
        this.notificationTime = notificationTime;
    }


}
