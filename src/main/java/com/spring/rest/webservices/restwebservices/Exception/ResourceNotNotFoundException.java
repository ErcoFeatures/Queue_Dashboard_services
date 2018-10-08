package com.spring.rest.webservices.restwebservices.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotNotFoundException extends RuntimeException {

    public ResourceNotNotFoundException(String message) {
        super(message);

    }
}
