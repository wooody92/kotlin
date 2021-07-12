package com.example.mvc.controller

import com.example.mvc.model.http.ValidationRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.lang.StringBuilder
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Validated
@RestController
@RequestMapping("/api")
class ValidationController {

    /**
     * VALIDATION - 1
     *
     * hibernate 기반의 bean validation은 bean에 대해 검증한다.
     * 검증하고자 하는 변수 age는 bean이 아니므로 이 @Validated를 이용하여 파라미터를 검증하도록 한다.
     *
     * 질문 : 조건을 충족하지 않으면 500 error로 표시되는 이유?
     */
    @GetMapping("/validate")
    fun validation(
            @Size(min = 2, max = 5)
            @NotNull
            @RequestParam name: String,

            @NotNull(message = "age should be exist")
            @Min(value = 20, message = "age should be over 20")
            @RequestParam age: Int
    ): ResponseEntity<String> {
        println(name)
        println(age)
        return ResponseEntity.badRequest().body("$name $age")
    }


    /**
     * VALIDATION - 2
     *
     * object 단일 검증 시에는 @Valid를 이용하고, object 내부 필드에 조건을 적용한다.
     * @Valid의 결과를 bindingResult로 담아 받을 수 있다.
     */
    @PostMapping("/validate")
    fun validation2(@Valid @RequestBody validationRequest: ValidationRequest, bindingResult: BindingResult): ResponseEntity<String> {

        if (bindingResult.hasErrors()) {
            // 400 error
            val msg = StringBuilder()
            bindingResult.allErrors.forEach {
                val field = it as FieldError
                val message = it.defaultMessage
                msg.append("${field.field} : $message\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }
        return ResponseEntity.ok().body(validationRequest.toString())
    }
}