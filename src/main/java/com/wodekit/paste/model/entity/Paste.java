package com.wodekit.paste.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wodekit.paste.model.entity.enums.PrivacyPolicy;
import lombok.Getter;
import lombok.Setter;

/**
 * @author banto
 */
@Getter
@Setter
public class Paste extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * Paste短代码
     */
    private String shortCode;

    /**
     * Paste名称
     */
    private String name;

    /**
     * Paste内容
     */
    private String content;

    /**
     * 高亮语法
     */
    private String syntax;

    /**
     * 访问策略
     */
    private PrivacyPolicy privacyPolicy;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 有效期(分钟)
     * 特殊值： -1: 永久有效；0 阅后即焚
     */
    private Long effective;

}
