package com.example.mvc.model.http

import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.*

data class ValidationRequest(

        @field:NotEmpty
        @field:Size(min = 2, max = 8)
        var name: String? = null,

        @field:PositiveOrZero // field >= 0
        var age: Int? = null,

        @field:Email
        var email: String? = null,

        @field:NotBlank
        var address: String? = null,

        @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
        var phoneNumber: String? = null,

        var createdAt: String? = null  // yyyy-MM-dd HH:mm:ss 2021-07-08 13:00:00
) {

    /**
     * TRUE: 정상, FALSE: 비정상
     *
     * 질문 : 메서드가 실행되는 시점은? 생성자가 만들어지는 시점에 검증하는 것인지?
     */
    @AssertTrue(message = "createdAt pattern should be 'yyyy-MM-dd HH:mm:ss")
    private fun isValidCreateAt(): Boolean {
        return try {
            LocalDateTime.parse(this.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            true
        } catch (e: Exception) {
            false
        }
    }
}
