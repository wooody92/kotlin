package com.example.mvc.controller

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PostController {

    /**
     * POST MAPPING - 1
     */
    @PostMapping("/post1")
    fun postMapping(): String {
        return "post-mapping"
    }

    /**
     * REQUEST MAPPING - 1
     */
    @RequestMapping(method = [RequestMethod.POST], path = ["/post2"])
    fun requestMapping(): String {
        return "request-mapping"
    }

    /**
     * REQUEST BODY - 1
     *
     * Object Mapper에 의해 json - object 자동 변환된다.
     * - data 들어올 때 json -> object
     * - data 나갈 때 object -> json
     */
    @PostMapping("/post3")
    fun postMappingObject(@RequestBody userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }
}