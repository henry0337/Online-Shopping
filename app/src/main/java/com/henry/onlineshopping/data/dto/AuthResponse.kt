package com.henry.onlineshopping.data.dto

data class AuthResponse(
    var token: String = "",
    var refreshToken: String = ""
)
