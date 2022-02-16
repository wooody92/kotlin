package com.example.demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurerSupport
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class DemoConfig : AsyncConfigurerSupport() {

    /**
     * corePoolSize: 기본적으로 실행을 대기하고 있는 thread 갯수
     * maxPoolSise: 동시 동작하는 최대 thread 갯수
     * queueCapacity : 동시에 동작하는 thread 갯수가 maxPoolSize 초과되는 요청 시, queue에 저장 후 순차적으로 실행
     * threadNamePrefix: spring이 생성하는 thread prefix
     */
    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 2
        executor.maxPoolSize = 10
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("demo-")
        executor.initialize()
        return executor
    }
}