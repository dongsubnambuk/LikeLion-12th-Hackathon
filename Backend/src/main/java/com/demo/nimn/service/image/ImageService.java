package com.demo.nimn.service.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {

    String uploadImage(MultipartFile image) throws IOException;
    String uploadByteImage(byte[] byteImage);
    byte[] downloadImage(Long id);
    String updateImage(Long id, MultipartFile newImageFile) throws IOException;
    Boolean deleteImage(Long id);
}
