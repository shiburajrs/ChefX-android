package com.example.chefx_android.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.chefx_android.databinding.ErrorStateViewBinding

class ErrorStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val binding = ErrorStateViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setImage(resId: Int) {
        binding.imgError.setImageResource(resId)
    }

    fun setOnRetry(callback: () -> Unit) {
        binding.btnRetry.setOnClickListener { callback() }
    }

    fun show(show: Boolean) {
        this.visibility = if (show) VISIBLE else GONE
    }
}