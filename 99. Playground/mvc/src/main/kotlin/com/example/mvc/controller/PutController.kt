package com.example.mvc.controller

import com.example.mvc.model.http.Result
import com.example.mvc.model.http.UserRequest
import com.example.mvc.model.http.UserResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PutController {

    /**
     * PUT MAPPING - 1
     */
    @PutMapping("/put1")
    fun putMapping(): String {
        return "put-mapping"
    }

    /**
     * REQUEST MAPPING - 1
     */
    @RequestMapping(method = [RequestMethod.PUT], path = ["/put2"])
    fun requestMapping(): String {
        return "request-mapping"
    }

    /**
     * REQUEST BODY - 1
     *
     * apply chaining pattern
     */
    @PutMapping(path = ["/put3"])
    fun putMappingObject(@RequestBody userRequest: UserRequest): UserResponse{
        // 0. Response
        return UserResponse().apply {
            // 1. result
            this.result = Result().apply {
                this.resultCode = "OK"
                this.resultMessage = "성공"
            }

        }.apply {
            // 2. description
            this.description = "description"

        }.apply {
            // 3. user mutable list
            val userList = mutableListOf<UserRequest>()
            userList.add(userRequest)
            userList.add(
                    UserRequest().apply {
                        this.name = "a"
                        this.age = 10
                        this.email = "a@email.com"
                        this.address = "a address"
                        this.phoneNumber = "010-1234-5678"
                    }
            )
            this.user = userList
        }
    }
}
