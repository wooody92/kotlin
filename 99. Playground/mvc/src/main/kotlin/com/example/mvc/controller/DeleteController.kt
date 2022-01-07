package com.example.mvc.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class DeleteController {

    /**
     * REQUEST PARAMETER - 1
     */
    @DeleteMapping("/delete1")
    fun deleteMapping(
            @RequestParam name: String,
            @RequestParam age: Int
    ): String {
        println(name)
        println(age)
        return "$name $age"
    }

    /**
     * PATH VARIABLE - 1
     *
     * path를 array로 받아서 여러 uri를 동시에 처리 할 수 있다.
     */
    @DeleteMapping(path = ["/delete2/name/{name}/age/{age}"])
    fun deleteMappingPath(@PathVariable(value = "name") _name: String, @PathVariable(name = "age") _age: Int): String {
        println(_name)
        println(_age)
        return "$_name $_age"
    }
}