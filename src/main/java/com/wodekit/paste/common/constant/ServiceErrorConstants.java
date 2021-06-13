package com.wodekit.paste.common.constant;

import com.wodekit.paste.common.exception.ServiceError;

/**
 * @author banto
 */
public class ServiceErrorConstants {

    public static final ServiceError UNKNOWN_ERROR = new ServiceError("UNKNOWN_ERROR", "系统发生未知错误");
    public static final ServiceError UNAUTHORIZED = new ServiceError("UNAUTHORIZED", "需授权后才可访问");
    public static final ServiceError PASTE_NOT_FOUND = new ServiceError("PASTE_NOT_FOUND", "Paste不存在");

}
