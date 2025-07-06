package com.demo.nimn.controller.image;

import com.demo.nimn.service.image.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "이미지 API", description = "이미지 업로드, 다운로드, 수정, 삭제")
@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Operation(summary = "이미지 업로드", description = "새로운 이미지를 업로드하여 저장합니다.")
    @PostMapping("")
    public ResponseEntity<String> uploadImage(
            @Parameter(description = "업로드할 이미지 파일", required = true)
            @RequestPart("image") MultipartFile image) throws IOException {
        String uploadImageUrl = imageService.uploadImage(image);

        return ResponseEntity.ok(uploadImageUrl);
    }

    @Operation(summary = "이미지 다운로드", description = "ID를 통해 저장된 이미지를 다운로드합니다.")
    @GetMapping(value = "/{imageId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> downloadImage(
            @Parameter(description = "다운로드할 이미지 ID", required = true, example = "1")
            @PathVariable("imageId") Long id) {
        byte[] byteImage = imageService.downloadImage(id);

        return ResponseEntity.ok(byteImage);
    }

    @Operation(summary = "이미지 수정", description = "기존 이미지를 새로운 이미지로 교체합니다.")
    @PutMapping("/{imageId}")
    public ResponseEntity<String> updateImage(
            @Parameter(description = "수정할 이미지 ID", required = true, example = "1")
            @PathVariable("imageId") Long id,
            @Parameter(description = "새로 업로드할 이미지 파일", required = true)
            @RequestPart("image") MultipartFile image) throws IOException {
        String uploadImageUrl = imageService.updateImage(id, image);

        return ResponseEntity.ok(uploadImageUrl);
    }

    @Operation(summary = "이미지 삭제", description = "ID를 통해 저장된 이미지를 삭제합니다.")
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Boolean> deleteImage(
            @Parameter(description = "삭제할 이미지 ID", required = true, example = "1")
            @PathVariable("imageId") Long id) {
        Boolean result = imageService.deleteImage(id);

        return ResponseEntity.ok(result);
    }
}