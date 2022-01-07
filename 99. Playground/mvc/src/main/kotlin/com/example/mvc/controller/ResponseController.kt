package com.example.mvc.controller

import com.example.mvc.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/response")
class ResponseController {

    /**
     * STATUS CODE 400
     */
    @GetMapping("/get1")
    fun getMapping(@RequestParam age: Int?): ResponseEntity<String> {

        if (age == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age should be exist")
        }
        if (age < 20) {
            return ResponseEntity.status(400).body("age should be over 20")
        }
        return ResponseEntity.ok("OK")
    }

    /**
     * STATUS CODE 400
     *
     * kotlin 표준함수 사용
     */
    @GetMapping("/get2")
    fun getMapping2(@RequestParam age: Int?): ResponseEntity<String> {

        return age?.let {
            // age not null
            if (age < 20) {
                return ResponseEntity.status(400).body("age should be over 20")
            }
            ResponseEntity.ok("OK")

        } ?: kotlin.run {
            // age is null
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age should be exist")
        }
    }

    /**
     * STATUS CODE 200
     */
    @PostMapping("")
    fun postMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(userRequest) // object mapper : object -> json
    }

    /**
     * STATUS CODE 201
     */
    @PutMapping("")
    fun putMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<UserRequest> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest)
    }

    /**
     * STATUS CODE 500
     */
    @DeleteMapping("/{id}")
    fun deleteMapping(@PathVariable id: Int): ResponseEntity<Nothing> {
        return ResponseEntity.status(500).body(null)
    }
}