package org.example.imageserver.dao;

import org.example.imageserver.Entity.ImageEntity;

import java.util.Optional;

public interface ImageDAO {
    public ImageEntity uploadImage(ImageEntity imageEntity);
    public Optional<ImageEntity> downloadImage(Long id);
    public boolean deleteImage(Long id);
}
