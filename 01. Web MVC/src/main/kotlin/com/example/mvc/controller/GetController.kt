package com.example.mvc.controller

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class GetController {

    /**
     * GET MAPPING - 1
     */
    @GetMapping("/get1")
    fun hello(): String {
        return "hello kotlin"
    }

    /**
     * REQUEST MAPPING - 1
     */
    @RequestMapping(method = [RequestMethod.GET], path = ["/get2", "/get3"])
    fun requestMapping(): String {
        return "hello world"
    }

    /**
     * PATH VARIABLE - 1
     */
    @GetMapping("/get4/{name}/{age}")
    fun pathVariable(@PathVariable name: String, @PathVariable age:Int): String {
        println("${name}, $age")
        return "hello $name $age"
    }

    /**
     * PATH VARIABLE - 2
     */
    @GetMapping("/get5/{name}/{age}")
    fun pathVariable2(@PathVariable(value = "name") _name: String, @PathVariable(name = "age") age:Int): String {
        val name = "kotlin"
        println("${_name}, $age")
        return "hello $_name $age"
    }

    /**
     * QUERY PARAMETER - 1
     */
    @GetMapping("/get6")
    fun queryParam(@RequestParam(value = "name") name: String, @RequestParam age: Int): String {
        println("${name}, $age")
        return "hello $name $age"
    }

    /**
     * QUERY PARAMETER - 2
     * 1. @RestController로 선언하면 object mapper에 의해 응답 객체가 json 형태로 변환된다.
     * 2. phone-number와 같이 하이픈이 포함된 uri는 객체 이름으로 선언할 수 없으므로, @RequestParam(value = "phone-number") 또는 아래의 map 방식으로 처리 가능하다.
     */
    @GetMapping("/get7")
    fun queryParamObject(userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    /**
     * QUERY PARAMETER - 3
     * 1. map으로 query param을 핸들링 할 수 있다.
     */
    @GetMapping("/get8")
    fun queryParamMap(@RequestParam map: Map<String, Any>): Map<String, Any> {
        println(map)
        val phoneNumber = map.get("phone-number")
        return map
    }
}
