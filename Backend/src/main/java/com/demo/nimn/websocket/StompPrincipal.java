package com.demo.nimn.websocket;

import java.security.Principal;

public class StompPrincipal implements Principal {

    private final String userEmail;


    public StompPrincipal(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String getName() {
        return userEmail;
    }
}