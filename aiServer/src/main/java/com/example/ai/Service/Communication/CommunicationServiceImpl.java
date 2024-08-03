package com.example.ai.Service.Communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunicationServiceImpl implements CommunicationService{
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(CommunicationServiceImpl.class);

    @Autowired
    public CommunicationServiceImpl(DiscoveryClient discoveryClient,
                                    RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @Override
    public String imageUpload(byte[] image) {
        try {
            ServiceInstance imageService = discoveryClient.getInstances("IMAGE-SERVER").get(0);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, byte[]> bodyMap = new HashMap<>();
            bodyMap.put("image", image);
            HttpEntity<Map<String, byte[]>> http = new HttpEntity<>(bodyMap, headers);
            logger.info("imageServer URL : " + imageService.getUri().toString());
            URI uri = new URI(imageService.getUri() + "/image/byteImage");
            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(uri, HttpMethod.POST, http, LinkedHashMap.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Successfully uploaded image");
                LinkedHashMap responseBody = response.getBody();
                List<String> images = (List<String>) responseBody.get("images");
                String imageUri = images.get(0);
                logger.info("Image URI: {}", imageUri);

                return imageUri;
            } else {
                logger.error("Failed to upload image, status code: {}", response.getStatusCode());
                throw new RuntimeException("Failed to upload image");
            }
        } catch (Exception e) {
            logger.error("Failed to upload image", e);
            throw new RuntimeException("Failed to upload image", e);
        }
    }

}
