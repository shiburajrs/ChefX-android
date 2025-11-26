package com.example.chefx_android.presentation.fragment.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chefx_android.R
import com.example.chefx_android.core.BundleArguments
import com.example.chefx_android.data.local.dto.RecipeModelEntity
import com.example.chefx_android.data.remote.dto.toMealItemList
import com.example.chefx_android.databinding.FragmentFavouriteBinding
import com.example.chefx_android.presentation.activity.meal_detail.MealDetailActivity
import com.example.chefx_android.presentation.adapters.TrendingRecipeAdapter
import com.example.chefx_android.presentation.fragment.home.HomeViewModel
import com.example.chefx_android.utils.ActivityUtils
import com.example.chefx_android.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupTrendingRecipes()
        viewModel.getFavouriteMeals()
    }

    private fun initView(){
        binding.abDetailAppBar.setTitle(getString(R.string.your_favourites))
        binding.abDetailAppBar.setBackButtonVisibility()
    }

    private fun setupTrendingRecipes(){
        viewModel.getFavouriteMealsSuccess.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                }

                is UiState.Success -> {
                    val responseBase = state.data as List<RecipeModelEntity>
                    val adapter = TrendingRecipeAdapter(
                        responseBase.toMealItemList(),
                        onItemClick = { recipe ->
                            val bundle = Bundle()
                            bundle.putString(BundleArguments.ARG_MEAL_ID, recipe.idMeal)
                            ActivityUtils.startActivity(
                                requireContext(),
                                MealDetailActivity::class.java,
                                bundle = bundle,
                            )
                        }
                    ) 

                    binding.rvFavouriteRecipes.adapter = adapter
                    binding.rvFavouriteRecipes.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                is UiState.Error -> {
                }
            }
        }
    }
}