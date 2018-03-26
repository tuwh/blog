package com.uncub.blog.common.exception;

public class ServiceException extends RuntimeException {
    private String errCode;
    private String errMsg;
    private Exception cause;
    public ServiceException(String errorCode){
        super();
        this.errCode = errorCode;
    }

    public ServiceException(){

    }
}
