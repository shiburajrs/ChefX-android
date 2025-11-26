package com.example.chefx_android.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.chefx_android.R
import com.example.chefx_android.data.remote.dto.MealDetail
import com.example.chefx_android.databinding.ItemTrendingRecipeBinding

class SearchRecipeAdapter(
    private val recipes: MutableList<MealDetail>,
    private val onClick: (MealDetail) -> Unit
) : RecyclerView.Adapter<SearchRecipeAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(val binding: ItemTrendingRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: MealDetail) {
            binding.tvRecipeTitle.text = recipe.strMeal

            Glide.with(binding.root.context)
                .load(recipe.strMealThumb)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .centerCrop()
                .transform(RoundedCorners(24))
                .into(binding.ivRecipeImage)

            binding.root.setOnClickListener {
                onClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemTrendingRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size

    fun updateData(newList: List<MealDetail>) {
        recipes.clear()
        recipes.addAll(newList)
        notifyDataSetChanged()
    }
}
