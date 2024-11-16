package com.henry.onlineshopping.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.onlineshopping.data.dto.AuthResponse
import com.henry.onlineshopping.data.model.User
import com.henry.onlineshopping.data.repository.AuthService
import com.henry.onlineshopping.utility.alias.MLD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    retrofit: Retrofit
) : ViewModel() {
    private val service = retrofit.create(AuthService::class.java)

    val authResponse = MLD<AuthResponse>()
    val user = MLD<User>()

    fun login() {
        viewModelScope.launch {
            val response = service.login()
            authResponse.value = response.body()
        }
    }

    fun register() {
        viewModelScope.launch {
            val response = service.register()
            user.value = response.body()
        }
    }
}