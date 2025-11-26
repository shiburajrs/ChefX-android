package com.example.chefx_android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chefx_android.R
import com.example.chefx_android.core.BaseActivity
import com.example.chefx_android.databinding.ActivityDashboardBinding
import com.example.chefx_android.presentation.fragment.favourite.FavouriteFragment
import com.example.chefx_android.presentation.fragment.home.HomeScreenFragment
import com.example.chefx_android.presentation.fragment.search.SearchFragment
import com.example.chefx_android.presentation.fragment.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdgeInsets(binding.root)
        loadFragment(HomeScreenFragment())

        binding.bottomNav.setOnItemSelectedListener { index ->
            when (index) {
                0 -> loadFragment(HomeScreenFragment())
                1 -> loadFragment(SearchFragment())
                2 -> loadFragment(FavouriteFragment())
                3 -> loadFragment(SettingsFragment())
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, fragment)
            .commit()
    }
}