package com.wodekit.paste.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author banto
 */
@Getter
@Setter
public class PasteMetadataResponse {

    private String shortCode;

    private String passwordPolicy;

    private boolean burnAfterReading;

}
