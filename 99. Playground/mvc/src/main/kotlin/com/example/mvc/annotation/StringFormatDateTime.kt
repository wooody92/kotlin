package com.example.mvc.annotation

import com.example.mvc.validator.StringFormatDateTimeValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * @Target : annotaion을 어디에 사용 할 지 설정
 * @Retention : annotation이 어느 시점에 동작할 지 설정
 *
 * @StringFormatDateTime 이 붙은 filed, getter, setter(targer으로 지정한)에 대해서,
 * @Constraint로 지정한 StringFormatDateTimeValidator로 검증한다.
 */
@Constraint(validatedBy = [StringFormatDateTimeValidator::class])
@Target(
        AnnotationTarget.FIELD,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class StringFormatDateTime (
        val pattern: String = "yyyy-MM-dd HH:mm:ss",
        val message: String = "time format is not allowed",
        val group: Array<KClass<*>> = [], // annotaion에 필요한 default group
        val payload: Array<KClass<out Payload>> = [] // annotaion에 필요한 default payload
)