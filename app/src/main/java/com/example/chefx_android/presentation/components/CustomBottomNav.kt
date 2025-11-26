package com.example.chefx_android.presentation.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.chefx_android.R
import androidx.core.graphics.toColorInt

class CustomBottomNav @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val selectedBgColor = ContextCompat.getColor(context, R.color.app_color)  // Red
    private val selectedIconColor = Color.WHITE
    private val unselectedIconColor =ContextCompat.getColor(context, R.color.inactive_grey)

    private var currentIndex = 0

    private var callback: ((Int) -> Unit)? = null

    private val iconFrames: List<FrameLayout>
    private val icons: List<ImageView>

    init {
        inflate(context, R.layout.bottom_nav_view, this)
        orientation = HORIZONTAL

        iconFrames = listOf(
            findViewById(R.id.itemHome),
            findViewById(R.id.itemSearch),
            findViewById(R.id.itemFav),
            findViewById(R.id.itemProfile)
        )

        icons = listOf(
            findViewById(R.id.navHome),
            findViewById(R.id.navSearch),
            findViewById(R.id.navFav),
            findViewById(R.id.navProfile)
        )

        initClicks()
        updateSelection()
    }

    private fun initClicks() {
        iconFrames.forEachIndexed { index, frame ->
            frame.setOnClickListener {
                currentIndex = index
                updateSelection()
                callback?.invoke(index)
            }
        }
    }

    private fun updateSelection() {
        icons.forEachIndexed { i, icon ->

            if (i == currentIndex) {
                // Perfect circle background ON ICON
                icon.background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(selectedBgColor)
                }
                icon.setColorFilter(selectedIconColor)
            } else {
                // Remove bg on unselected icons
                icon.background = null
                icon.setColorFilter(unselectedIconColor)
            }
        }
    }


    fun setOnItemSelectedListener(listener: (Int) -> Unit) {
        callback = listener
    }
}
