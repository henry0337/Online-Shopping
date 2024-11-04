package com.henry.onlineshopping.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.henry.onlineshopping.R
import com.henry.onlineshopping.data.model.Item
import com.henry.onlineshopping.databinding.ActivityDetailBinding
import com.henry.onlineshopping.ui.adapter.PictureListAdapter
import com.henry.onlineshopping.ui.adapter.SizeAdapter
import com.henry.onlineshopping.utility.helper.CartManagement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: Item
    private lateinit var manager: CartManagement
    private var orderCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

        manager = CartManagement(this)

        getBundleExtra()
        initList()
    }

    @SuppressLint("SetTextI18n")
    private fun getBundleExtra() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            item = intent.getParcelableExtra("item", Item::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            item = intent.getParcelableExtra("item")!!
        }

        binding.apply {
            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = "$${item.price}"
            ratingDetailTxt.text = item.rating.toString()
            sellerName.text = item.sellerName

            addToCartBtn.setOnClickListener {
                item.cartProductCount = orderCount
                CoroutineScope(Dispatchers.IO).launch {
                    manager.insertItem(item)
                }
            }

            backBtn.setOnClickListener {
                finish()
            }

            goToCartBtn.setOnClickListener {
                startActivity(Intent(this@DetailActivity, CartActivity::class.java))
            }

            Glide.with(this@DetailActivity)
                .load(item.sellerAvatar)
                .apply(RequestOptions().transform(CenterCrop()))
                .into(sellerImg)

            callBtn.setOnClickListener {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.sellerTel.toString(), null)))
            }

            messageBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setData(Uri.parse("sms:${item.sellerTel}"))
                    putExtra("sms_body", "Type your message")
                }

                startActivity(intent)
            }
        }
    }

    private fun initList() {
        val picList = mutableListOf<String>()
        val sizeList = mutableListOf<String>()

        picList.addAll(item.pictureUrl)
        picList.addAll(item.size)

        Glide.with(this)
            .load(picList[0])
            .into(binding.detailImg)

        binding.imgList.apply {
            adapter = PictureListAdapter(picList, binding.detailImg)
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.sizeList.apply {
            adapter = SizeAdapter(sizeList)
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}