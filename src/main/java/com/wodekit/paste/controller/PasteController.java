package com.wodekit.paste.controller;

import com.wodekit.paste.model.entity.Paste;
import com.wodekit.paste.model.request.CreatePasteRequest;
import com.wodekit.paste.model.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author banto
 */
@Slf4j
@Api("Paste控制器")
@RestController
@RequestMapping("/paste")
public class PasteController {

    @PostMapping
    @ApiOperation("创建Paste")
    public ApiResponse create(@Validated CreatePasteRequest request) {
        return ApiResponse.success();
    }

    @GetMapping("/metadata/{shortCode}")
    @ApiOperation("获取Paste元数据")
    public ApiResponse getMetadata(@PathVariable("shortCode") String shortCode) {
        Paste paste = new Paste();

        return ApiResponse.success();
    }

    @GetMapping("/{shortCode}")
    public ApiResponse getDetail(@PathVariable("shortCode") String shortCode) {
        return ApiResponse.success();
    }
}
