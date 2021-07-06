package com.example.mvc.model.http

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

/**
 * json <-> object 변환전략
 * 1. @JsonProperty
 * 2. @JsonNaming
 * 3. application-propertis : object mapper 설정 변경
 * 4. object mapper bean 설정 시 변경
 */
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class UserRequest(
        var name:String?=null,
        var age:Int?=null,
        var email:String?=null,
        var address:String?=null,

        @JsonProperty("phone_number")
        var phoneNumber:String?=null
)
