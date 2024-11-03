package com.henry.onlineshopping.data.repository

import com.henry.onlineshopping.data.dto.AuthResponse
import com.henry.onlineshopping.data.model.User
import com.henry.onlineshopping.utility.constant.Endpoint
import retrofit2.Response
import retrofit2.http.POST

interface AuthService {

    @POST(Endpoint.LOGIN)
    suspend fun login(): Response<AuthResponse>

    @POST(Endpoint.REGISTER)
    suspend fun register(): Response<User>

    @POST(Endpoint.CHANGE_PASSWORD)
    suspend fun changePassword(): Response<AuthResponse>

    @POST(Endpoint.USER_INFO)
    suspend fun getUserInfo(): Response<User>
}