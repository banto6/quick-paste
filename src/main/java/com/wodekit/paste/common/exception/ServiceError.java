package com.wodekit.paste.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author banto
 */
@Getter
@AllArgsConstructor
public class ServiceError {

    /**
     * 错误代码
     */
    private String code;

    /**
     * 错误描述
     */
    private String description;

}
