package com.wodekit.paste.service;

import com.wodekit.paste.model.entity.Paste;

/**
 * @author banto
 */
public interface PasteService {

    /**
     * 根据短代码获取Paste元数据
     * @param shortCode
     * @return
     */
    Paste getMetadataByShortCode(String shortCode);
}
