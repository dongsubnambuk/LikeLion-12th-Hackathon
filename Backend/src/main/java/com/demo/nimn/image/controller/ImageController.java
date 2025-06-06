package com.demo.nimn.image.controller;


import com.demo.nimn.image.dto.ImageDTO;
import com.demo.nimn.image.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/image")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;

    public ImageController(@Autowired ImageService imageService){
        this.imageService = imageService;
    }
    
    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> uploadImage(@RequestPart List<MultipartFile> images) throws IOException {
        logger.info("[upload image]");
        return this.imageService.uploadImage(images);
    }


    @PostMapping(value = "/test")
    public ResponseEntity<ImageDTO> alspdlpsldsp(
            @RequestBody Map<String, String> value
            )
    {
        System.out.println(value);
        return this.imageService.uploadImages(value.get("url"));
    }


    @PostMapping(value = "/byteImage")
    public ResponseEntity<ImageDTO> uploadByteImage(
            @RequestBody Map<String, byte[]> value
    )
    {
        System.out.println(value);
        return this.imageService.uploadByteImage(value.get("image"));
    }


    @GetMapping(value = "/download/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public  ResponseEntity<byte[]> downloadImage(
            @PathVariable("id") Long id
    ){
        logger.info("[download image] " + id);
        return this.imageService.downloadImage(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateImage(
            @PathVariable("id") Long id,
            @RequestPart("image") MultipartFile image
    ) throws IOException {
        return this.imageService.updateImage(id, image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteImage(
            @PathVariable("id") Long id
    ){
        logger.info("[delete Image] "+id);
        return this.imageService.deleteImage(id);
    }

}
