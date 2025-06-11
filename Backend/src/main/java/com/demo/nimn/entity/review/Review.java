package com.demo.nimn.entity.review;

import com.demo.nimn.entity.meal.FoodMenu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "t_review")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "food_menu_id", referencedColumnName = "id")
    private FoodMenu foodMenu;

    private Long likes;

    private Long disLikes;

    @ElementCollection
    @CollectionTable(name = "t_review_comment", joinColumns = @JoinColumn)
    @Column(name = "comment")
    private List<String> comment;

    @ManyToMany(mappedBy = "reviews")
    private List<DailyReview> dailyReviews;

    public void incrementLikes(){
        this.likes++;
    }

    public void incrementDisLikes(){
        this.disLikes++;
    }
}
