package com.henry.onlineshopping.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.onlineshopping.data.model.Slider
import com.henry.onlineshopping.data.repository.SliderService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import retrofit2.Retrofit

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val retrofit: Retrofit
) : ViewModel() {

    private val listOfSliders = MutableLiveData<List<Slider>>()
    private val service = retrofit.create(SliderService::class.java)

    fun fetchAll() {
        viewModelScope.launch {
            val response = service.getAllSlider()
            listOfSliders.value = response.body()
        }
    }
}