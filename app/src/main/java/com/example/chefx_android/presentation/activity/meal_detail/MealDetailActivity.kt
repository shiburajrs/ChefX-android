package com.example.chefx_android.presentation.activity.meal_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chefx_android.R
import com.example.chefx_android.core.BaseActivity
import com.example.chefx_android.core.BundleArguments
import com.example.chefx_android.data.local.dto.toEntity
import com.example.chefx_android.data.remote.dto.MealDetailResponse
import com.example.chefx_android.data.remote.dto.getIngredientList
import com.example.chefx_android.databinding.ActivityMealDetailBinding
import com.example.chefx_android.presentation.adapters.IngredientAdapter
import com.example.chefx_android.presentation.fragment.home.HomeViewModel
import com.example.chefx_android.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMealDetailBinding
    private val viewModel: HomeViewModel by viewModels()

    private var mealId: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdgeInsets(binding.root)
        setupRecipeDetailView()
        getDateArguments()
        initView()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun initView(){
        binding.abDetailAppBar.setTitle(getString(R.string.recipe_details))
        binding.ivRecipeFav.setOnClickListener(this)
        binding.abDetailAppBar.onBackClick {
            onBackPressedDispatcher.onBackPressed()
        }
        viewModel.addToFavSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this,
                    "Success", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this,
                    "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDateArguments(){
         mealId = intent.extras?.getString(BundleArguments.ARG_MEAL_ID)
        viewModel.getRecipeDetail(mealId ?: "")
    }

    private fun setupRecipeDetailView(){
        viewModel.mealDetailRecipeState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.evErrorState.visibility = View.GONE
                    binding.llContentView.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.evErrorState.visibility = View.GONE
                    binding.llContentView.visibility = View.VISIBLE
                    val responseBase = state.data
                    setMealDetailView(responseBase)
                }

                is UiState.Error -> {
                    binding.evErrorState.visibility = View.VISIBLE
                    binding.llContentView.visibility = View.GONE
                }
            }
        }
    }

    private fun setMealDetailView(mealDetail: MealDetailResponse){
        Glide.with(this)
            .load(mealDetail.meals.first().strMealThumb)
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

        binding.tvMealName.text = mealDetail.meals.first().strMeal
        binding.tvMealDescription.text = mealDetail.meals.first().strInstructions
        binding.btSource.setOnClickListener(this)
        binding.btYoutube.setOnClickListener(this)
        viewModel.youtubeUrl = mealDetail.meals.first().strYoutube
        viewModel.sourceUrl = mealDetail.meals.first().strSource
        val ingredientList = mealDetail.meals.first().getIngredientList()

        binding.rvIngredients.apply {
            layoutManager = LinearLayoutManager(this@MealDetailActivity)
            adapter = IngredientAdapter(ingredientList)
        }
    }

    override fun onClick(v: View?) {
       when(v?.id) {
           R.id.btSource -> {
               startActivity(Intent(Intent.ACTION_VIEW, viewModel.sourceUrl?.toUri()))

           }

           R.id.btYoutube -> {
               startActivity(Intent(Intent.ACTION_VIEW, viewModel.youtubeUrl?.toUri()))

           }

           R.id.ivRecipeFav -> {
               lifecycleScope.launch {
                   binding.ivRecipeFavIcon.isSelected = !binding.ivRecipeFavIcon.isSelected
                   viewModel.recipeModelResponse?.meals?.first()?.toEntity()?.let {
                       viewModel.addRecipeToFavourite(it)
                   }
               }
           }
       }
    }

}