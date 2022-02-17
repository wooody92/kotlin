package com.example.demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync

@Configuration
@EnableAsync
class DemoConfig {
    // thread pool 설정은 application.yml 정의
}
