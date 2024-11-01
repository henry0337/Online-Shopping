package com.henry.onlineshopping.data.repository

import com.henry.onlineshopping.data.model.Category
import com.henry.onlineshopping.data.model.Item
import com.henry.onlineshopping.data.model.Slider
import retrofit2.Response
import retrofit2.http.GET

interface DashboardService {

    @GET("api/v1/banner")
    suspend fun getAllSlider(): Response<List<Slider>>

    @GET("api/v1/category")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("api/v1/product")
    suspend fun getAllBestSeller(): Response<List<Item>>
}