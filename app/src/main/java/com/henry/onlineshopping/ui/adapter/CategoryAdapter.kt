package com.henry.onlineshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.henry.onlineshopping.data.model.Category
import com.henry.onlineshopping.databinding.ViewholderCategoryBinding

class CategoryAdapter(
    private var items: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleCategory.text = item.title

        Glide.with(holder.itemView.context)
            .load(item.previewImg)
            .into(holder.binding.imageView3)
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root)
}