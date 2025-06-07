package com.demo.nimn.entity.review;

import com.example.ai.Entity.Meal.FoodMenu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;
    @OneToOne
    @JoinColumn(name = "food_menu_id", referencedColumnName = "foodMenuId")
    private FoodMenu foodMenu;
    @Column
    private Long likes;
    @Column
    private Long disLikes;

    @ElementCollection
    @CollectionTable(name = "review_comments", joinColumns = @JoinColumn(name = "review_id"))
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
