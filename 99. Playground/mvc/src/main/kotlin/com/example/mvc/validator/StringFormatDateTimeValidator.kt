package com.example.mvc.validator

import com.example.mvc.annotation.StringFormatDateTime
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * ConstraintValidator<CustomAnnotation, inputValue>
 */
class StringFormatDateTimeValidator : ConstraintValidator<StringFormatDateTime, String> {

    private var pattern: String? = null

    // custom annotation으로 지정한 pattern을 현재 pattern으로 주입
    override fun initialize(constraintAnnotation: StringFormatDateTime?) {
        this.pattern = constraintAnnotation?.pattern
    }

    // 정상: True, 비정상: False
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return try {
            LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern))
            true
        } catch (e: Exception) {
            false
        }
    }
}