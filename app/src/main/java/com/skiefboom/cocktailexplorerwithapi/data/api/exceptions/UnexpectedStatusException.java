package com.skiefboom.cocktailexplorerwithapi.data.api.exceptions;

public class UnexpectedStatusException extends Exception {

    private Integer statusCode;
    private Integer errorCode;
    private String response;

    public UnexpectedStatusException(String detailMessage, Integer statusCode, String response, Integer errorCode) {

        super(detailMessage);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.response = response;
    }


    public Integer getErrorCode() {

        return errorCode;
    }

    public int getStatusCode() {

        return statusCode;
    }

    public String getResponse() {

        return response;
    }
}
