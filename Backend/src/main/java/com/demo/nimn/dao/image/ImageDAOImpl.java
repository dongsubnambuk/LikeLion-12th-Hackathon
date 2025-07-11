package com.demo.nimn.dao.image;

import com.demo.nimn.entity.image.Image;
import com.demo.nimn.repository.image.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ImageDAOImpl implements ImageDAO{
    private static final Logger logger = LoggerFactory.getLogger(ImageDAOImpl.class);
    private final ImageRepository imageRepository;

    public ImageDAOImpl(@Autowired ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }


    @Override
    public Image uploadImage(Image image) {
        return this.imageRepository.save(image);
    }

    @Override
    public Optional<Image> downloadImage(Long id) {
        return this.imageRepository.findById(id);
    }

    @Override
    public boolean deleteImage(Long id) {
        if(!this.imageRepository.existsById(id)){
            return false;
        }
        this.imageRepository.deleteById(id);
        return true;
    }
}
