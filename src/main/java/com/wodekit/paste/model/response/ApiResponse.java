package com.wodekit.paste.model.response;

import com.wodekit.paste.common.exception.ServiceError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author banto
 */
@Getter
@AllArgsConstructor
public class ApiResponse {

    private String code;

    private String message;

    private Object data;

    public static ApiResponse success() {
        return new ApiResponse("OK", "", null);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse("OK", "", data);
    }

    public static ApiResponse fail(ServiceError error) {
        return new ApiResponse(error.getCode(), error.getDescription(), null);
    }
}
