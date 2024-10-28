package com.henry.onlineshopping.ui.screen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.henry.onlineshopping.R
import com.henry.onlineshopping.data.model.Slider
import com.henry.onlineshopping.databinding.ActivityDashboardBinding
import com.henry.onlineshopping.ui.adapter.BestSellerAdapter
import com.henry.onlineshopping.ui.adapter.CategoryAdapter
import com.henry.onlineshopping.ui.adapter.SliderAdapter
import com.henry.onlineshopping.ui.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val window = window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.apply {
                systemUiVisibility = 0
            }
        }

        getBanners()
        getCategories()
        getBestSeller()
    }

    private fun getCategories() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.listOfCategories.observe(this) { categories ->
            binding.recyclerViewCategory.apply {
                layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = CategoryAdapter(categories)
            }
            binding.progressBar.visibility = View.GONE
        }

        viewModel.fetchAllCategories()
    }

    private fun getBanners() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.listOfSliders.observe(this) { banner ->
            setupBanners(banner)
            binding.progressBar.visibility = View.GONE
        }

        viewModel.fetchAllSlider()
    }

    private fun setupBanners(banners: List<Slider>) {
        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.viewPager2.apply {
            adapter = SliderAdapter(banners, binding.viewPager2)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
        }

        if (banners.size > 1) {
            binding.dotsIndicator.apply {
                visibility = View.VISIBLE
                attachTo(binding.viewPager2)
            }
        }
    }

    private fun getBestSeller() {
        binding.progressBarBs.visibility = View.VISIBLE
        viewModel.listOfItems.observe(this) { items ->
            binding.recyclerViewBs.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerViewBs.adapter = BestSellerAdapter(items)
            binding.progressBarBs.visibility = View.GONE
        }
        viewModel.fetchAllBestSeller()
    }
}