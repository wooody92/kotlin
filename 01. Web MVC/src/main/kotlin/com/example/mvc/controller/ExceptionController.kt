package com.example.mvc.controller

import com.example.mvc.model.http.Error
import com.example.mvc.model.http.ErrorResponse
import com.example.mvc.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Validated
@RestController
@RequestMapping("/api")
class ExceptionController {

    @GetMapping("/exception1")
    fun hello() {
        val list = mutableListOf<String>()
        val temp = list[0]  // IndexOutOfBoundsException
    }

    @GetMapping("/exception2")
    fun get(
            @NotBlank
            @Size(min = 2, max = 6)
            @RequestParam name: String,

            @Min(10)
            @RequestParam age: Int
    ): String {
        return "$name $age"
    }

    @PostMapping("/exception3")
    fun post(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        return userRequest
    }

    /**
     * PATH - /api/exception1
     *
     * Exception 처리의 범위를 해당 컨트롤러로 한정하여 처리 할 수 있다.
     */
    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundException(e: IndexOutOfBoundsException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error on Controller")
    }

    /**
     * REQUEST PARAMETER ERROR HANDLER - 1
     * PATH - /api/exception2
     *
     * HttpServletRequest를 매개변수로 받으면 Spring에서 현재 request를 찾아서 넣어준다.
     *
     * 질문 - 해당 Exception 못잡고 500 error 발생하는 중인데 어디가 문제인지 모르겠음
     */
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(e: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {

        val errors = mutableListOf<Error>()

        // ConstraintViolationException이 갖고있는 에러를 가져올 수 있다.
        e.constraintViolations.forEach {
            val error = Error().apply {
                this.field = it.propertyPath.last().name // propertyPath의 마지막에 변수 이름이 지정되어 있어 last로 가져온다.
                this.message = it.message
                this.value = it.invalidValue
            }
            errors.add(error)
        }

        // 2. ErrorResponse
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생했습니다."
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
        }

        // 3. ResponseEntity
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /**
     * REQUEST BODY ERROR HANDLER - 1
     * PATH - /api/exception3
     *
     * bindingResult로 에러를 가져온다.
     */
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {

        val errors = mutableListOf<Error>()

        e.bindingResult.allErrors.forEach { errorObject ->
            val error = Error().apply {
                val field = errorObject as FieldError    // 형 변환
                this.field = field.field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue
            }
            errors.add(error)
        }

        val errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생했습니다."
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}