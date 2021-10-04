package com.adja.evchargerappserver.api.abstracts;

public class NotValidUpdateException extends Exception {

    public NotValidUpdateException(String errorMessage) {
        super(errorMessage);
    }
}
