package com.example.todo.repository

import com.example.todo.database.Todo
import com.example.todo.database.TodoDataBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.LocalDateTime

@Service
class TodoRepositoryImpl : TodoRepository {

    @Autowired
    lateinit var todoDataBase: TodoDataBase

    override fun save(todo: Todo): Todo? {
        return todo.index?.let { index ->
            // update
            findOne(index)?.apply {
                this.title = todo.title
                this.description = todo.description
                this.schedule = todo.schedule
                this.updatedAt = LocalDateTime.now()
            }
        } ?: kotlin.run {
            // save
            todo.apply {
                this.index = ++todoDataBase.index
                this.createdAt = LocalDateTime.now()
                this.updatedAt = LocalDateTime.now()
            }.run {
                todoDataBase.todoList.add(todo)
                this    // return todo
            }
        }

//        // kotlin style
//        return todo.apply {
//            this.index = ++todoDataBase.index
//            this.createdAt = LocalDateTime.now()
//            this.updatedAt = LocalDateTime.now()
//        }.run {
//            todoDataBase.todoList.add(todo)
//            this    // return todo
//        }
//        // java style
//        val index = todoDataBase.index++
//        todo.index = index
//        todoDataBase.todoList.add(todo)
    }

    override fun saveAll(todoList: MutableList<Todo>): Boolean {
        return try {
            todoList.forEach {
                save(it)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(index: Int): Boolean {
        val todo = findOne(index)

        return todo?.let {
            todoDataBase.todoList.remove(it)
            true
        } ?: kotlin.run {
            false
        }
    }

    /**
     * nullable return
     */
    override fun findOne(index: Int): Todo? {
        // TODO : 존재하지 않는 index 조회 시 error 처리
        // filter{ it.index == index }.first()
        return todoDataBase.todoList.first { it.index == index }
    }

    override fun findAll(): MutableList<Todo> {
        return todoDataBase.todoList
    }

}