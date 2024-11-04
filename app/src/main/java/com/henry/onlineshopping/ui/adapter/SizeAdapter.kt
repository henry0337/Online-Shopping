package com.henry.onlineshopping.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.henry.onlineshopping.R
import com.henry.onlineshopping.databinding.ViewholderSizeBinding

class SizeAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ViewholderSizeBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = holder.bindingAdapterPosition
        if (currentPosition == RecyclerView.NO_POSITION) return

        holder.binding.apply {
            size.text = items[currentPosition]
            root.setOnClickListener {
                lastSelectedPosition = selectedPosition
                selectedPosition = currentPosition
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
            }

            if (selectedPosition == position) {
                sizeLayout.setBackgroundResource(R.drawable.brown_bg_2)
                size.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                sizeLayout.setBackgroundResource(R.drawable.grey_bg)
                size.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ViewholderSizeBinding) : RecyclerView.ViewHolder(binding.root)
}