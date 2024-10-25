package com.henry.onlineshopping.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.onlineshopping.data.model.Category
import com.henry.onlineshopping.data.model.Slider
import com.henry.onlineshopping.data.repository.DashboardService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import retrofit2.Retrofit

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val retrofit: Retrofit
) : ViewModel() {

    private val service = retrofit.create(DashboardService::class.java)
    val listOfSliders = MutableLiveData<List<Slider>>()
    val listOfCategories = MutableLiveData<List<Category>>()

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
}