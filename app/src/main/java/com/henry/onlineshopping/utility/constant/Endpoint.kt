package com.henry.onlineshopping.utility.constant

object Endpoint {
    private const val BASE_REQUEST_URL = "/api/v1"

    // Authentication route
    const val LOGIN = "$BASE_REQUEST_URL/login"
    const val REGISTER = "$BASE_REQUEST_URL/register"
    const val USER_INFO = "$BASE_REQUEST_URL/userinfo"
    const val CHANGE_PASSWORD = "$BASE_REQUEST_URL/changePassword"
}