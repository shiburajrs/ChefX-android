package com.example.chefx_android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.chefx_android.databinding.ActivityMainBinding
import com.example.chefx_android.presentation.DashboardActivity
import com.example.chefx_android.utils.ActivityUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.holo_red_dark)
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true
        redirectToHome()
    }

    private fun redirectToHome(){
        Handler(Looper.getMainLooper()).postDelayed({
            ActivityUtils.startActivity(this, DashboardActivity::class.java)
        }, 1500)

    }
}