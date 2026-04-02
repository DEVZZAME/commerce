package zzame.commerce.common.controller

import zzame.commerce.common.response.HealthCheckResponse
import zzame.commerce.common.response.RestResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/health")
    fun health(): RestResponse<HealthCheckResponse> = RestResponse.success(
        HealthCheckResponse(status = "ok"),
    )
}
