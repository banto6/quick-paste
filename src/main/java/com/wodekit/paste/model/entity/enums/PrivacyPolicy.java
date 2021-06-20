package com.wodekit.paste.model.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;

/**
 * @author banto
 */
@AllArgsConstructor
public enum PrivacyPolicy {

    NONE("NONE"),
    AUTHORIZATION("AUTHORIZATION"),
    ENCRYPTED("ENCRYPTED");

    @EnumValue
    private String code;

    public String getCode() {
        return code;
    }
}
