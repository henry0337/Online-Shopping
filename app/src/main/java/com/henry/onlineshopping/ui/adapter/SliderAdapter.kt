package com.henry.onlineshopping.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.henry.onlineshopping.R
import com.henry.onlineshopping.data.model.Slider

class SliderAdapter(
    slider: List<Slider>,
    viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    private lateinit var slider: List<Slider>
    private lateinit var viewPager: ViewPager2
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider_image_container, parent, false))

    override fun getItemCount() = slider.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setImage(slider[position], context)

        if (position == slider.size - 1) {
            viewPager.post {
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageSlide)

        fun setImage(model: Slider, context: Context) {
            val requestOptions = RequestOptions().transform(CenterInside())

            Glide.with(context)
                .load(model.url)
                .apply(requestOptions)
                .into(imageView)
        }
    }
}