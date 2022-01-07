package com.example.mvc.controller

import com.example.mvc.model.http.UserRequest
import com.example.mvc.model.http.ValidationRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest // WebMvc 관련 Spring boot 기능을 load
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("GET 요청에 대해 200 OK 성공하는 테스트 : GetController")
    fun get_test() {
        mockMvc.perform(
                get("/api/get1")
        ).andExpect(
                status().isOk
        ).andExpect(
                content().string("hello kotlin")
        ).andDo(print())
    }

    @Test
    @DisplayName("GET query parameter 요청에 대해 200 OK 성공하는 테스트 : GetController")
    fun get_queryParam_test() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "henry")
        queryParams.add("age", "20")

        mockMvc.perform(
                get("/api/get6").queryParams(queryParams)
        ).andExpect(
                status().isOk
        ).andExpect(
                content().string("hello henry 20")
        ).andDo(print())
    }

    /**
     * 정상 요청에 대해 ExceptionHandler가 catch 하지 못해 400이 아닌 500 error 발생으로 테스트 실패
     */
    @Test
    @DisplayName("GET query parameter 요청에 대해 400 응답하는 테스트 : ExceptionController")
    fun get_queryParam_fail_test() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "henry")
        queryParams.add("age", "9")

        mockMvc.perform(
                get("/api/exception2").queryParams(queryParams)
        ).andExpect(
                status().isBadRequest
        ).andExpect(
                content().contentType("application/json")
        ).andExpect(
                jsonPath("\$.result_code").value("FAIL")
        ).andExpect(
                jsonPath("\$.errors[0].field").value("age")
        ).andExpect(
                jsonPath("\$.errors[0].value").value("9")
        ).andDo(print())
    }

    @Test
    @DisplayName("POST 요청에 대해 200 OK 성공하는 테스트 : ExceptionController")
    fun post_test() {
        val userRequest = UserRequest().apply {
            this.name = "henry"
            this.age = 20
            this.email = "henry@email.com"
            this.address = "경기도 성남시"
            this.phoneNumber = "010-1234-5678"
        }

        // Object -> Json 변환
        val objectToJson = jacksonObjectMapper().writeValueAsString(userRequest)
        println(objectToJson)

        mockMvc.perform(
                post("/api/exception3")
                        .content(objectToJson)
                        .contentType("application/json")
                        .accept("application/json")
        ).andExpect(
                status().isOk
        ).andExpect(
                jsonPath("\$.name").value("henry")
        ).andExpect(
                jsonPath("\$.age").value(20)
        ).andExpect(
                jsonPath("\$.email").value("henry.email.com")
        ).andDo(
                print()
        )
    }

    /**
     * 정상 요청에 대해 ExceptionHandler가 catch 하지 못해 400이 아닌 500 error 발생으로 테스트 실패
     */
    @Test
    @DisplayName("POST 요청에 대해 400 응답하는 테스트 : ValidationController")
    fun post_fail_test() {
        val validationRequest = ValidationRequest().apply {
            this.name = "henry"
            this.age = -10  // bad request
            this.email = "henry@email.com"
            this.address = "경기도 성남시"
            this.phoneNumber = "010-1234-5678"
            this.createdAt = "2021-07-12 13:00:00"
        }

        // Object -> Json 변환
        val objectToJson = jacksonObjectMapper().writeValueAsString(validationRequest)
        println(objectToJson)

        mockMvc.perform(
                post("/api//validate")
                        .content(objectToJson)
                        .contentType("application/json")
                        .accept("application/json")
        ).andExpect(
                status().isBadRequest
        ).andDo(
                print()
        )
    }
}