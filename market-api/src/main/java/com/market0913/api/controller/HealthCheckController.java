package com.market0913.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "헬스체크 API")
public class HealthCheckController {

    @GetMapping("/health-check")
    @ApiOperation(value = "헬스 체크 API")
    public Long healthCheck() {
        return System.currentTimeMillis();
    }
}
