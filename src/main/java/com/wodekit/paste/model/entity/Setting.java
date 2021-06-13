package com.wodekit.paste.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * @author banto
 */
@Getter
@Setter
public class Setting extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String key;

    private String value;

}
