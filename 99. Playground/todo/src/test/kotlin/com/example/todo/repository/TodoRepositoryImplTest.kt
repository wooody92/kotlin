package com.example.todo.repository

import com.example.todo.config.AppConfig
import com.example.todo.database.Todo
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

/**
 * ExtendWith : 확장 기능을 사용한다.
 * SpringBootTest : 테스트 시 필요한 클래스만 불러오도록 한다.
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TodoRepositoryImpl::class, AppConfig::class])
class TodoRepositoryImplTest {

    @Autowired
    lateinit var todoRepositoryImpl: TodoRepositoryImpl

    @BeforeEach
    fun before() {
        todoRepositoryImpl.todoDataBase.init()
    }

    @Test
    fun saveTest() {
        // given
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "테스트"
            this.schedule = LocalDateTime.now()
        }

        // when
        val result = todoRepositoryImpl.save(todo)

        // then
        assertThat(result?.index).isEqualTo(1)
        assertThat(result?.title).isEqualTo("테스트 일정")
        assertThat(result?.description).isEqualTo("테스트")
        assertThat(result?.createdAt).isNotNull
        assertThat(result?.updatedAt).isNotNull
    }

    @Test
    fun saveAllTest() {
        // given
        val todoList = mutableListOf(
                Todo().apply {
                    this.title = "테스트 일정"
                    this.description = "테스트"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "테스트 일정2"
                    this.description = "테스트2"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "테스트 일정3"
                    this.description = "테스트3"
                    this.schedule = LocalDateTime.now()
                }
        )

        // when
        val result = todoRepositoryImpl.saveAll(todoList)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun findOneTest() {
        // given
        val todoList = mutableListOf(
                Todo().apply {
                    this.title = "테스트 일정"
                    this.description = "테스트"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "테스트 일정2"
                    this.description = "테스트2"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "테스트 일정3"
                    this.description = "테스트3"
                    this.schedule = LocalDateTime.now()
                }
        )
        todoRepositoryImpl.saveAll(todoList)

        // when
        val result = todoRepositoryImpl.findOne(2)

        // then
        assertThat(result).isNotNull
        assertThat(result?.title).isEqualTo("테스트 일정2")
    }

    @Test
    fun updateTest() {
        // given
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "테스트"
            this.schedule = LocalDateTime.now()
        }
        val indexTodo = todoRepositoryImpl.save(todo)

        val newTodo = Todo().apply {
            this.index = indexTodo?.index
            this.title = "업데이트 일정"
            this.description = "업데이트 테스트"
            this.schedule = LocalDateTime.now()
        }

        // when
        val result = todoRepositoryImpl.save(newTodo)

        // then
        assertThat(result).isNotNull
        assertThat(indexTodo?.index).isEqualTo(result?.index)
        assertThat(result?.title).isEqualTo("업데이트 일정")
        assertThat(result?.description).isEqualTo("업데이트 테스트")
    }
}