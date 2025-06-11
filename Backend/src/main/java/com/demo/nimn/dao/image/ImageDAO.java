package com.demo.nimn.dao.image;

import com.demo.nimn.entity.image.Image;

import java.util.Optional;

public interface ImageDAO {
    public Image uploadImage(Image image);
    public Optional<Image> downloadImage(Long id);
    public boolean deleteImage(Long id);
}
