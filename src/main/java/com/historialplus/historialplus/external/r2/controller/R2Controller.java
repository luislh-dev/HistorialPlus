package com.historialplus.historialplus.external.r2.controller;

import com.historialplus.historialplus.external.r2.service.CloudflareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/r2")
@RequiredArgsConstructor
public class R2Controller {

    private final CloudflareService cloudflareService;

    @GetMapping("/presigned-url")
    public String generatePresignedUrl(@RequestParam String objectKey) throws Exception {
        return cloudflareService.generatePresignedUrl(objectKey);
    }
}
