package com.wynn.springsec.dto;

public class SuccessResponse {
    private String message;
    
    public SuccessResponse(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
