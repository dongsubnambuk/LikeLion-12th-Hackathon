package com.demo.nimn.image.service;

import com.demo.nimn.image.dto.ImageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ImageService {
    public ResponseEntity<ImageDTO> uploadImage(List<MultipartFile> images) throws IOException;
    public ResponseEntity<ImageDTO> uploadImages(String url);

    public ResponseEntity<ImageDTO> uploadByteImage(byte[] byteImage);

    public ResponseEntity<byte[]> downloadImage(Long id);
    public ResponseEntity<String> updateImage(Long id, MultipartFile image) throws IOException;
    public ResponseEntity<Boolean> deleteImage(Long id);


}
