package com.wodekit.paste.common.exception;

/**
 * 业务异常类
 * @author banto
 */
public class ServiceException extends RuntimeException {

    private ServiceError error;

    public ServiceException(ServiceError error) {
        this.error = error;
    }

    public ServiceError getError() {
        return error;
    }
}
