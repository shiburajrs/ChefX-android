package com.example.chefx_android.presentation.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chefx_android.R
import com.example.chefx_android.core.BundleArguments
import com.example.chefx_android.data.remote.dto.RandomRecipeResponse
import com.example.chefx_android.databinding.FragmentHomeScreenBinding
import com.example.chefx_android.presentation.adapters.TrendingRecipeAdapter
import com.example.chefx_android.presentation.activity.meal_detail.MealDetailActivity
import com.example.chefx_android.utils.ActivityUtils
import com.example.chefx_android.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

@AndroidEntryPoint
class HomeScreenFragment() : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel: HomeViewModel by viewModels()
    private var randomMealId: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        setupRandomRecipeView()
        setupTrendingRecipes()
        binding.llRandomRecipeView.setOnClickListener(this)
        viewModel.getRandomRecipes()
        viewModel.getTrendingRecipes()
    }

    private fun setupRandomRecipeView(){
        viewModel.randomRecipeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.shimmerContainer.visibility = View.VISIBLE
                    binding.shimmerContainer.startShimmer()
                    binding.llRandomRecipeView.visibility = View.GONE
                    binding.evErrorState.show(false)
                }

                is UiState.Success -> {

                    binding.shimmerContainer.visibility = View.GONE
                    binding.shimmerContainer.stopShimmer()
                    binding.llRandomRecipeView.visibility = View.VISIBLE
                    binding.evErrorState.show(false)
                    val responseBase = state.data as RandomRecipeResponse
                    randomMealId = responseBase.meals?.first()?.idMeal
                    Glide.with(requireContext())
                        .load(responseBase.meals.first().strMealThumb)
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .apply(
                            RequestOptions.bitmapTransform(
                                RoundedCornersTransformation(
                                    16,
                                    0,  // margin
                                    RoundedCornersTransformation.CornerType.ALL
                                )
                            )
                        )
                        .into(binding.ivRandomRecipeImage)

                    binding.tvRandomRecipeName.text = responseBase.meals.first().strMeal

                }

                is UiState.Error -> {
                    binding.shimmerContainer.visibility = View.GONE
                    binding.shimmerContainer.stopShimmer()
                    binding.llRandomRecipeView.visibility = View.GONE
                    binding.evErrorState.show(true)
                }
            }
        }
    }

    private fun setupTrendingRecipes(){
        viewModel.trendingRecipeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.sfTrendingRecipes.visibility = View.VISIBLE
                    binding.rvTrendingRecipes.visibility = View.GONE
                    binding.sfTrendingRecipes.startShimmer()
                }

                is UiState.Success -> {
                    binding.sfTrendingRecipes.visibility = View.GONE
                    binding.rvTrendingRecipes.visibility = View.VISIBLE
                    binding.sfTrendingRecipes.stopShimmer()
                    val responseBase = state.data
                    val adapter = TrendingRecipeAdapter(
                        responseBase.meals,
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

                    binding.rvTrendingRecipes.adapter = adapter
                    binding.rvTrendingRecipes.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                is UiState.Error -> {
                    binding.sfTrendingRecipes.visibility = View.GONE
                    binding.rvTrendingRecipes.visibility = View.VISIBLE
                    binding.sfTrendingRecipes.stopShimmer()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.llRandomRecipeView -> {
                val bundle = Bundle()
                bundle.putString(BundleArguments.ARG_MEAL_ID, randomMealId)
                ActivityUtils.startActivity(requireContext(), MealDetailActivity::class.java,bundle = bundle,)
            }
        }
    }

}