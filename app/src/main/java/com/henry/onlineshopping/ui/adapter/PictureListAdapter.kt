package com.henry.onlineshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.henry.onlineshopping.databinding.ViewholderPiclistBinding

class PictureListAdapter(
    private val items: List<String>,
    private val picture: ImageView,
) : RecyclerView.Adapter<PictureListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(ViewholderPiclistBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position])
            .into(holder.binding.picList)

        holder.binding.root.setOnClickListener {
            Glide.with(holder.itemView.context)
                .load(items[position])
                .into(picture)
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ViewholderPiclistBinding) : RecyclerView.ViewHolder(binding.root)
}