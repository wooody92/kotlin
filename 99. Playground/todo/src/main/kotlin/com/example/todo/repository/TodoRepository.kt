package com.example.todo.repository

import com.example.todo.database.Todo

interface TodoRepository {

    fun save(todo: Todo): Todo? // index 있으면 update, 없으면 save
    fun saveAll(todoList: MutableList<Todo>): Boolean

    fun delete(index: Int): Boolean

    fun findOne(index: Int): Todo?
    fun findAll(): MutableList<Todo>
}