package com.wodekit.paste.model.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;

/**
 * @author banto
 */
@AllArgsConstructor
public enum PasswordPolicy {

    NONE("NONE"),
    ACCESS("ACCESS"),
    DATA("DATA");

    @EnumValue
    private String code;

    public String getCode() {
        return code;
    }
}
