package com.example.todo.config

import com.example.todo.database.TodoDataBase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    /**
     * Spring App 실행 시점에 TodoDataBase class를 생성하고 init method를 실행
     */
    @Bean(initMethod = "init")
    fun todoDataBase(): TodoDataBase {
        return TodoDataBase()
    }
}