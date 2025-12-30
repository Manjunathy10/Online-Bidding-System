package com.substring.auth.exception;

public class UnauthorizedAuctionAccessException extends RuntimeException {
    public UnauthorizedAuctionAccessException(String msg) {
        super(msg);
    }
}