package com.wodekit.paste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author banto
 */
@Controller
public class RawController {

    @GetMapping("/raw/{shortCode}")
    @ResponseBody
    public String raw(
            @PathVariable("shortCode") String shortCode,
            @RequestParam(value = "dl", defaultValue = "0", required = false) int dl
    ) {
        return shortCode + dl;
    }

}
