package com.adja.evchargerappserver.api;

public class NotValidUpdateException extends Exception {

    public NotValidUpdateException(String errorMessage) {
        super(errorMessage);
    }
}
