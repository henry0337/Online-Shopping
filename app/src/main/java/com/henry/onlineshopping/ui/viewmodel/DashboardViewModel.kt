package com.henry.onlineshopping.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.onlineshopping.data.model.Category
import com.henry.onlineshopping.data.model.Item
import com.henry.onlineshopping.data.model.Slider
import com.henry.onlineshopping.data.repository.DashboardService
import com.henry.onlineshopping.utility.alias.MLLD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    retrofit: Retrofit
) : ViewModel() {

    private val service = retrofit.create(DashboardService::class.java)
    val listOfSliders = MLLD<Slider>()
    val listOfCategories = MLLD<Category>()
    val listOfItems = MLLD<Item>()

    fun fetchAllSlider() {
        viewModelScope.launch {
            val response = service.getAllSlider()
            listOfSliders.value = response.body()
        }
    }

    fun fetchAllCategories() {
        viewModelScope.launch {
            val response = service.getAllCategories()
            listOfCategories.value = response.body()
        }
    }

    fun fetchAllBestSeller() {
        viewModelScope.launch {
            val response = service.getAllBestSeller()
            listOfItems.value = response.body()
        }
    }
}