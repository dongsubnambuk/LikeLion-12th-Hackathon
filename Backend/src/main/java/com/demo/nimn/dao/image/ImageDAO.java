package com.demo.nimn.dao.image;

import com.demo.nimn.entity.image.ImageEntity;

import java.util.Optional;

public interface ImageDAO {
    public ImageEntity uploadImage(ImageEntity imageEntity);
    public Optional<ImageEntity> downloadImage(Long id);
    public boolean deleteImage(Long id);
}
