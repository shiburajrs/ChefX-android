package com.example.chefx_android.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.chefx_android.R
import com.example.chefx_android.databinding.ViewCustomAppbarBinding

class CustomAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {

    private val binding: ViewCustomAppbarBinding

    init {
        orientation = HORIZONTAL
        val inflater = LayoutInflater.from(context)
        binding = ViewCustomAppbarBinding.inflate(inflater, this, true)
    }

    fun setTitle(title: String) {
        binding.tvToolbarTitle.text = title
    }

    fun onBackClick(listener: () -> Unit) {
        binding.ivBackIcon.setOnClickListener { listener() }
    }

    fun setBackButtonVisibility(){
        binding.ivBackIcon.visibility = INVISIBLE
    }
}
