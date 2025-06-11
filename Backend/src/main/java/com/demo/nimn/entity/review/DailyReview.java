package com.demo.nimn.entity.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_daily_review")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private LocalDate reviewDate;

//    @OneToMany
//    @JoinColumn
//    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "t_daily_review_review",
            joinColumns = @JoinColumn(name = "daily_review_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id")
    )
    private List<Review> reviews;
}