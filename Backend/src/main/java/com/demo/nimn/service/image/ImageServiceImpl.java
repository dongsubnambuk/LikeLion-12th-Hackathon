package com.demo.nimn.service.image;

import com.demo.nimn.entity.image.Image;
import com.demo.nimn.repository.image.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        Image savedImage = this.imageRepository.save(Image.builder().byteImage(image.getBytes()).build());

        return "/api/image/" + savedImage.getId();
    }

    @Override
    public String uploadByteImage(byte[] byteImage) {
        Image image = this.imageRepository.save(Image.builder().byteImage(byteImage).build());

        return "/api/image/" + image.getId();
    }


    @Override
    public byte[] downloadImage(Long id) {
        Image image = this.imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image Not Found"));

        return image.getByteImage();
    }

    @Override
    public String updateImage(Long id, MultipartFile newImageFile) throws IOException {
        Image oldImage = this.imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image Not Found"));

        Image newImage = Image.builder()
                .id(oldImage.getId())
                .byteImage(newImageFile.getBytes())
                .build();

        Image savedImage = this.imageRepository.save(newImage);

        return "/api/image/" + savedImage.getId();
    }

    @Override
    public Boolean deleteImage(Long id) {
        imageRepository.deleteById(id);

        return !imageRepository.existsById(id);
    }
}