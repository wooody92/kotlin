package com.example.todo.model.http

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.validation.FieldError
import javax.validation.Validation

class TodoDtoTest {

    val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun todoDtoTest() {
        // given
        val todoDto = TodoDto().apply {
            this.title = "타이틀"
            this.description = ""
            this.schedule = "2021-07-13 13:00:00"
        }

        // when
        val result = validator.validate(todoDto)


        // then
        assertThat(result).isEmpty()

        result.forEach {
            println(it.propertyPath.last().name)
            println(it.message)
            println(it.invalidValue)
        }
    }
}