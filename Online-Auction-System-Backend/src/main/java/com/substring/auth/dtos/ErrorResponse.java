package com.substring.auth.dtos;

import org.springframework.http.HttpStatusCode;

public record ErrorResponse(String message, HttpStatusCode status ) {

}
