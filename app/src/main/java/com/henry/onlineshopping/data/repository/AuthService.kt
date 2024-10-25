package com.henry.onlineshopping.data.repository

import retrofit2.http.POST

interface AuthService {
    @POST("/")
    fun login()
    fun register()
    fun changePassword()
    fun getUserInfo()
}