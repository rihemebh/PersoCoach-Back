package com.website.persocoach.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientNotFoundExceptionResponse {
    private String client_id;

    public ClientNotFoundExceptionResponse(String client_id) {
        this.client_id = client_id;
    }

    public String getProjectIdentifier() {
        return client_id;
    }

    public void setProjectIdentifier(String client_id) {
        this.client_id = client_id;
    }
}
