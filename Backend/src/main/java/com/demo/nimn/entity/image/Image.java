package com.demo.nimn.entity.image;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] byteImage;
}
