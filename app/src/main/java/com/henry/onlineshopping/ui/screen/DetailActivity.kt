package com.henry.onlineshopping.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.henry.onlineshopping.R
import com.henry.onlineshopping.data.model.Item
import com.henry.onlineshopping.databinding.ActivityDetailBinding

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

        manager = CartManagement(this)

        getBundleExtra()
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
                manager.addToCart(item)
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
                // TODO: Handle message button click
            }
        }
    }
}