package com.henry.onlineshopping.data.repository

import com.henry.onlineshopping.data.model.Slider
import retrofit2.Response
import retrofit2.http.GET

interface SliderService {

    @GET("api/v1/slider")
    suspend fun getAllSlider(): Response<List<Slider>>
}