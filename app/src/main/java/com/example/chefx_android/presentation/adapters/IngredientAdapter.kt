package com.example.chefx_android.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chefx_android.data.remote.dto.IngredientResponse
import com.example.chefx_android.databinding.ItemIngredientBinding

class IngredientAdapter(
    private val list: List<IngredientResponse>
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(
        val binding: ItemIngredientBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = list[position]

        holder.binding.apply {
            tvIngredient.text = item.name
            tvMeasure.text = item.measure
        }
    }

    override fun getItemCount(): Int = list.size
}
