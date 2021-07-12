package com.example.todo.database

/**
 * JPA를 사용하지 않기 때문에 임의로 데이터베이스를 생성
 */
data class TodoDataBase(
        var index: Int = 0,
        var todoList: MutableList<Todo> = mutableListOf()
) {
    fun init() {
        this.index = 0
        this.todoList = mutableListOf()
        println("[DEBUG] todo database init")
    }
}
