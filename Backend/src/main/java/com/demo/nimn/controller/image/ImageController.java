package com.demo.nimn.controller.image;

import com.demo.nimn.service.image.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "이미지 API", description = "")
@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("")
    public ResponseEntity<String> uploadImage(@RequestPart("image") MultipartFile image) throws IOException {
        String uploadImageUrl = imageService.uploadImage(image);

        return ResponseEntity.ok(uploadImageUrl);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> downloadImage(@PathVariable("id") Long id) {
        byte[] byteImage = imageService.downloadImage(id);

        return ResponseEntity.ok(byteImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateImage(@PathVariable("id") Long id,
                                              @RequestPart("image") MultipartFile image) throws IOException {
        String uploadImageUrl = imageService.updateImage(id, image);

        return ResponseEntity.ok(uploadImageUrl);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteImage(@PathVariable("id") Long id) {
        Boolean result = imageService.deleteImage(id);

        return ResponseEntity.ok(result);
    }
}
