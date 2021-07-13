package com.example.todo.database

import com.example.todo.model.http.TodoDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * value? = null 설정으로 매개변수가 없는 기본 생성자를 가져간다.
 */
data class Todo(
        var index: Int? = null,                 // 일정 index
        var title: String? = null,              // 일정 타이틀
        var description: String? = null,        // 일정 설명
        var schedule: LocalDateTime? = null,    // 일정 시간
        var createdAt: LocalDateTime? = null,   // 생성 시간
        var updatedAt: LocalDateTime? = null    // 업데이트 시간
)

/**
 * DTO to Entity
 *
 * 1. 코드로 적용 - 확장 함수
 * 2. model mapper 적용
 * 3. kotlin reflection 적용
 */
fun Todo.dtoToEntity(todoDto: TodoDto): Todo {
    return Todo().apply {
        this.index = todoDto.index
        this.title = todoDto.title
        this.description = todoDto.description
        this.schedule = LocalDateTime.parse(todoDto.schedule, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        this.createdAt = todoDto.createdAt
        this.updatedAt = todoDto.updatedAt
    }
}
