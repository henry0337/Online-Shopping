package com.henry.onlineshopping.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.henry.onlineshopping.data.model.Item
import com.henry.onlineshopping.databinding.ViewholderBestSellerBinding

class BestSellerAdapter(
    private val items: List<Item>
) : RecyclerView.Adapter<BestSellerAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        return ViewHolder(ViewholderBestSellerBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            titleTxt.text = item.title
            priceTxt.text = "\$${item.price}"
            ratingTxt.text = item.rating.toString()

            Glide.with(context)
                .load(item.pictureUrl[0])
                .apply(RequestOptions().transform(CenterCrop()))
                .into(picBestSeller)
        }

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ViewholderBestSellerBinding) : RecyclerView.ViewHolder(binding.root)
}