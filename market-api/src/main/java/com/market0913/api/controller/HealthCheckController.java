package com.market0913.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "헬스체크", description = "헬스 체크 API")
public class HealthCheckController {

    @GetMapping("/health-check")
    @Operation(summary = "헬스 체크 API")
    @ApiResponse(responseCode = "200", description = "현재 시간 (unix timestamp)")
    public Long healthCheck() {
        return System.currentTimeMillis();
    }
}
