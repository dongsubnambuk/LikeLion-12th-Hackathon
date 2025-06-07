package com.demo.nimn.service.payment;

import com.example.paymentserver.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

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
    public List<String> readAllUser() {
        try {
            ServiceInstance userService = discoveryClient.getInstances("AUTH-SERVER").get(0);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> http = new HttpEntity<>(headers);
            logger.info("AuthService URL : " + userService.getUri().toString());
            URI uri = new URI(userService.getUri() + "/api/users/all");
            ResponseEntity<UserDTO> response = restTemplate.exchange(uri, HttpMethod.GET, http, UserDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Successfully fetched all users");
                UserDTO users = response.getBody();
                logger.info("Number of users retrieved: {}", users.getEmail().size());

                return users.getEmail();
            } else {
                logger.error("Failed to fetch users, status code: {}", response.getStatusCode());
                throw new RuntimeException("Failed to fetch users");
            }
        } catch (Exception e) {
            logger.error("Failed to fetch users", e);
            throw new RuntimeException("Failed to fetch users", e);
        }
    }
}