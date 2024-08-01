package com.example.ai.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
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
    public String imageUpload(byte[] image) throws URISyntaxException {
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

    @Override
    public String imageUpdate(String image, String imageKey) throws URISyntaxException{
        try {
            ServiceInstance imageService = discoveryClient.getInstances("IMAGE-SERVER").get(0);
            HttpHeaders headers = new HttpHeaders();

            Map<String, String> bodyMap = new HashMap<>();
            ResponseEntity response;

            if (imageKey != null) {
                bodyMap.put("image", image);
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<?> http = new HttpEntity<>(bodyMap, headers);
                URI uri = new URI(imageService.getUri() + "/api/image/" + imageKey);
                response = restTemplate.exchange(uri, HttpMethod.PUT, http, String.class);
                logger.info("ImageServer PUT Method");

                if (response.getStatusCode().is2xxSuccessful()) {
                    logger.info("Successfully updated image with key: {}", imageKey);
                    String imageUri = (String) response.getBody();

                    return imageUri;
                }
            } else {
                bodyMap.put("image", image);
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<?> http = new HttpEntity<>(bodyMap, headers);
                URI uri = new URI(imageService.getUri() + "/api/image/upload");
                response = restTemplate.exchange(uri, HttpMethod.POST, http, LinkedHashMap.class);
                logger.info("ImageServer POST method");

                if (response.getStatusCode().is2xxSuccessful()) {
                    logger.info("Successfully uploaded image");
                    LinkedHashMap responseBody = (LinkedHashMap) response.getBody();
                    List<String> images = (List) responseBody.get("images");
                    String imageUri = (String) images.get(0);

                    return imageUri;
                }
            }

        } catch (HttpClientErrorException e) {
            logger.error("Failed to upload/update image");
            return "Failed to upload image";
        }
        logger.error("Failed to upload/update image");
        return "Failed to upload image";
    }

    @Override
    public Boolean imageDelete(String imageKey) throws URISyntaxException{
        try {
            ServiceInstance imageService = discoveryClient.getInstances("IMAGE-SERVER").get(0);
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> http = new HttpEntity<>(headers);

            ResponseEntity response;

            if (imageKey != null) {
                URI uri = new URI(imageService.getUri() + "/api/image/" + imageKey);
                response = restTemplate.exchange(uri, HttpMethod.DELETE, http, Boolean.class);
                logger.info("ImageServer DELETE Method");
                return (Boolean) response.getBody();
            } else {
                return true;
            }
        } catch (HttpClientErrorException e) {
            logger.error("Failed to delete image with key: {}", imageKey);
            return false;
        }
    }
}
