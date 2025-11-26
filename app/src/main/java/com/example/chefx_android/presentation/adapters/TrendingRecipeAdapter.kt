package com.example.chefx_android.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.chefx_android.R
import com.example.chefx_android.data.remote.dto.MealItem
import com.example.chefx_android.databinding.ItemTrendingRecipeBinding

class TrendingRecipeAdapter(
    private val items: List<MealItem>,
    private val onItemClick: (MealItem) -> Unit
) : RecyclerView.Adapter<TrendingRecipeAdapter.TrendingViewHolder>() {

    inner class TrendingViewHolder(val binding: ItemTrendingRecipeBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding = ItemTrendingRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            tvRecipeTitle.text = item.strMeal

            // Item click
            llMainView.setOnClickListener {
                onItemClick(item)
            }

            Glide.with(root.context)
                .load(item.strMealThumb)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .transform(RoundedCorners(24))
                .into(ivRecipeImage)
        }
    }

    override fun getItemCount() = items.size
}
