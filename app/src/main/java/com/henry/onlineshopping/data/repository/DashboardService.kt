package com.henry.onlineshopping.data.repository

import com.henry.onlineshopping.data.model.Category
import com.henry.onlineshopping.data.model.Item
import com.henry.onlineshopping.data.model.Slider
import retrofit2.Response
import retrofit2.http.GET

interface DashboardService {

    @GET("api/v1/banners")
    suspend fun getAllSlider(): Response<List<Slider>>

    @GET("api/v1/categories")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("api/v1/products")
    suspend fun getAllBestSeller(): Response<List<Item>>
}