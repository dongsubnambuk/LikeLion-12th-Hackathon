package com.demo.nimn.service.image;

import com.demo.nimn.entity.image.Image;
import com.demo.nimn.dao.image.ImageDAO;
import com.demo.nimn.dto.image.ImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);


//    private String imageURI = "http://localhost:11000";
    private String imageURI = "http://3.37.64.39:8000";

    @Autowired
    @Qualifier("generalRestTemplate")
    private RestTemplate restTemplate;

    private final ImageDAO imageDAO;
//    private static final Long[] ids = {1L, 2L, 3L};

    public ImageServiceImpl(@Autowired ImageDAO imageDAO){
        this.imageDAO = imageDAO;
    }

    @Override
    public ResponseEntity<ImageDTO> uploadImage(List<MultipartFile> images) throws IOException {
        List<String> imagesURI = new ArrayList<>();
        for(MultipartFile image : images){
            Image imageEntity = this.imageDAO.uploadImage(Image.builder().image(image.getBytes()).build());
            imagesURI.add( imageURI + "/api/image/download/"+imageEntity.getId());
        }

        return ResponseEntity.status(201)
                .body(ImageDTO.builder()
                        .result("success")
                        .images(imagesURI).build());
    }

    @Override
    public ResponseEntity<ImageDTO> uploadImages(String url) {
        // 1. 받은 url을 restTemplate 요청을 보내서 byte[]로 받는다.

        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        byte[] image = response.getBody();

//        System.out.println(Arrays.toString(image));
//        return null;

        // 2. 앞에서 받은 byte[]을 디비에 저장한다.
        Image imageEntity = this.imageDAO.uploadImage(Image.builder().image(image).build());
        // 3. 도메인 + 이미지 다운로드 받은 uri를 만들어서 반환한다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ImageDTO.builder().images(Arrays.asList( imageURI + "/api/image/download/" + imageEntity.getId())).build());

    }

    @Override
    public ResponseEntity<ImageDTO> uploadByteImage(byte[] byteImage) {
        // 2. 앞에서 받은 byte[]을 디비에 저장한다.
        Image image = this.imageDAO.uploadImage(Image.builder().image(byteImage).build());
        // 3. 도메인 + 이미지 다운로드 받은 uri를 만들어서 반환한다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ImageDTO.builder().images(Arrays.asList( imageURI + "/api/image/download/" + image.getId())).build());

    }


    @Override
    public ResponseEntity<byte[]> downloadImage(Long id) {
        Optional<Image> image = this.imageDAO.downloadImage(id);
        if(!image.isPresent())
            return ResponseEntity.status(404).body(null);

        return ResponseEntity.status(200)
                .body(image.get().getImage());
    }

    @Override
    public ResponseEntity<String> updateImage(Long id, MultipartFile image) throws IOException {
        Optional<Image> optionalImage = this.imageDAO.downloadImage(id);
        if(!optionalImage.isPresent()){
            return ResponseEntity.status(400).body(null);
        }
        Image imageEntity = optionalImage.get();
        imageEntity = Image.builder()
                .id(imageEntity.getId())
                .image(image.getBytes())
                .build();
        imageEntity = this.imageDAO.uploadImage(imageEntity);

        return ResponseEntity.status(200).body(imageURI + "/api/image/download/"+imageEntity.getId());
    }

    @Override
    public ResponseEntity<Boolean> deleteImage(Long id) {
//        for(Long i : ids){
//            if(i.equals(id)) return ResponseEntity.status(200).body(false);
//        }

        if(!this.imageDAO.deleteImage(id)){
            return ResponseEntity.status(400).body(false);
        }
        return ResponseEntity.status(200).body(true);
    }





}
