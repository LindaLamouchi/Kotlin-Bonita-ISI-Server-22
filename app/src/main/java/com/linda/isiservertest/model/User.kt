package com.linda.isiservertest.model

data class User (
    val username:String,
    val password:String,
    val redirect:Boolean =false
)