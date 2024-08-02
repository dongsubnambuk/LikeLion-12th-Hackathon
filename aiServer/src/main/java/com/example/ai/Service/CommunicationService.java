package com.example.ai.Service;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

public interface CommunicationService {
    public String imageUpload(byte[] image);
    public String imageUpdate(String image, String imageKey) throws URISyntaxException;
    public Boolean imageDelete(String imageKey) throws URISyntaxException;
}
