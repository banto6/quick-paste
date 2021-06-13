package com.wodekit.paste.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author banto
 */
@Getter
@Setter
public class CreatePasteRequest {

    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "语法高亮选项不能为空")
    private String syntax;

    @NotBlank(message = "有效期不能为空")
    private String effective;

    @NotBlank(message = "访问策略不能为空")
    private String encryptionStrategy;

    private String password;

}
