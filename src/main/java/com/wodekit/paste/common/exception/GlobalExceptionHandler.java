package com.wodekit.paste.common.exception;

import com.wodekit.paste.common.constant.ServiceErrorConstants;
import com.wodekit.paste.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理器
 * @author banto
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public ApiResponse handleServiceException(ServiceException e) {
        return ApiResponse.fail(e.getError());
    }

    /**
     * 异常兜底
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse handleUnknownException(Exception e) {

        log.error("系统发生未知错误", e);

        return ApiResponse.fail(ServiceErrorConstants.UNKNOWN_ERROR);
    }
}
