package com.example.chefx_android.presentation.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chefx_android.R
import com.example.chefx_android.core.BundleArguments
import com.example.chefx_android.databinding.FragmentSearchBinding
import com.example.chefx_android.presentation.adapters.SearchRecipeAdapter
import com.example.chefx_android.presentation.fragment.home.HomeViewModel
import com.example.chefx_android.presentation.activity.meal_detail.MealDetailActivity
import com.example.chefx_android.utils.ActivityUtils
import com.example.chefx_android.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: HomeViewModel by viewModels()
    private var searchJob: Job? = null
    private lateinit var searchAdapter: SearchRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchRecipeView()
        binding.abDetailAppBar.setTitle(getString(R.string.search_recipes_text))
        binding.abDetailAppBar.setBackButtonVisibility()
        viewModel.getSearchMeal(binding.etSearch.text.toString())
        searchAdapter = SearchRecipeAdapter(mutableListOf()) { recipe ->
            val bundle = Bundle().apply {
                putString(BundleArguments.ARG_MEAL_ID, recipe.idMeal)
            }
            ActivityUtils.startActivity(requireContext(), MealDetailActivity::class.java, bundle)
        }

        binding.rvSearchRecipes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSearchRecipes.adapter = searchAdapter

        setSearchView()
    }

    private fun setSearchView(){
        binding.etSearch.addTextChangedListener { text ->
            val query = text.toString().trim()

            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(300)   // debounce time
                if (query.isNotEmpty()) {
                    viewModel.getSearchMeal(query)
                }
            }
        }
    }

    private fun setupSearchRecipeView(){
        viewModel.mealSearchRecipeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.sfSearchRecipes.visibility = View.VISIBLE
                    binding.rvSearchRecipes.visibility = View.GONE
                    binding.sfSearchRecipes.startShimmer()
                    binding.evErrorState.show(false)
                }

                is UiState.Success -> {
                    binding.sfSearchRecipes.visibility = View.GONE
                    binding.rvSearchRecipes.visibility = View.VISIBLE
                    binding.sfSearchRecipes.stopShimmer()
                    binding.evErrorState.show(false)
                    val mealsList = state.data.meals ?: emptyList()
                    searchAdapter.updateData(mealsList)


                }

                is UiState.Error -> {
                    binding.sfSearchRecipes.visibility = View.GONE
                    binding.sfSearchRecipes.stopShimmer()
                    binding.rvSearchRecipes.visibility = View.GONE
                    binding.evErrorState.show(true)
                }
            }
        }
    }

}