package com.henry.onlineshopping.data.repository

import com.henry.onlineshopping.data.dto.AuthResponse
import com.henry.onlineshopping.data.model.User
import com.henry.onlineshopping.utility.constant.Endpoint
import retrofit2.http.POST

interface AuthService {

    @POST(Endpoint.LOGIN)
    fun login(): AuthResponse

    @POST(Endpoint.REGISTER)
    fun register(): User

    @POST(Endpoint.CHANGE_PASSWORD)
    fun changePassword(): AuthResponse

    @POST(Endpoint.USER_INFO)
    fun getUserInfo(): User
}