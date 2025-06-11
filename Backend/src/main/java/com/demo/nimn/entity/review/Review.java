package com.demo.nimn.entity.review;

import com.demo.nimn.entity.meal.FoodMenu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_review")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "food_menu_id")
    private FoodMenu foodMenu;

    @Builder.Default
    private Long likes = 0L;

    @Builder.Default
    private Long disLikes = 0L;

    @ElementCollection
    @CollectionTable(name = "t_review_comment", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "comment")
    @Builder.Default
    private List<String> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "reviews")
    @Builder.Default
    private List<DailyReview> dailyReviews = new ArrayList<>();

    public void incrementLikes() {
        this.likes++;
    }

    public void incrementDisLikes() {
        this.disLikes++;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }
}