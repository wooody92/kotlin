package com.example.todo.service

import com.example.todo.database.Todo
import com.example.todo.database.dtoToEntity
import com.example.todo.model.http.TodoDto
import com.example.todo.model.http.entityToDto
import com.example.todo.repository.TodoRepositoryImpl
import org.springframework.stereotype.Service

@Service
class TodoService(
        val todoRepositoryImpl: TodoRepositoryImpl  // 생성자
) {
    fun create(todoDto: TodoDto): TodoDto? {
        return todoDto.let {
            Todo().dtoToEntity(it)
        }.let {
            todoRepositoryImpl.save(it)
        }?.let {
            TodoDto().entityToDto(it)
        }
    }

    fun read(index: Int): TodoDto? {
        return todoRepositoryImpl.findOne(index)?.let {
            TodoDto().entityToDto(it)
        }
    }

    fun readAll(): MutableList<TodoDto> {
        return todoRepositoryImpl.findAll()
                .map { TodoDto().entityToDto(it) }
                .toMutableList()
    }

    fun update(todoDto: TodoDto): TodoDto? {
        return todoDto.let {
            Todo().dtoToEntity(it)
        }.let {
            todoRepositoryImpl.save(it)
        }?.let {
            TodoDto().entityToDto(it)
        }
    }

    fun delete(index: Int): Boolean {
        return todoRepositoryImpl.delete(index)
    }
}