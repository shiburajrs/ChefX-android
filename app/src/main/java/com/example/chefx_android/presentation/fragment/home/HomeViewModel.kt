package com.example.chefx_android.presentation.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefx_android.data.local.dto.RecipeModelEntity
import com.example.chefx_android.data.remote.dto.MealDetailResponse
import com.example.chefx_android.data.remote.dto.RecipeItem
import com.example.chefx_android.data.remote.dto.TrendingRecipesResponse
import com.example.chefx_android.domain.repository.RecipeDatabaseRepository
import com.example.chefx_android.domain.repository.RecipeRepository
import com.example.chefx_android.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseRepo: RecipeDatabaseRepository,
    private val repo: RecipeRepository
) : ViewModel() {

    private val _recipesState = MutableLiveData<UiState<List<RecipeItem>>>()
    val recipesState: LiveData<UiState<List<RecipeItem>>> get() = _recipesState

    private val _randomRecipeState = MutableLiveData<UiState<Any>>()
    val randomRecipeState: LiveData<UiState<Any>> get() = _randomRecipeState

    private val _trendingRecipeState = MutableLiveData<UiState<TrendingRecipesResponse>>()
    val trendingRecipeState: LiveData<UiState<TrendingRecipesResponse>> get() = _trendingRecipeState
    //MealDetailResponse

    private val _mealDetailRecipeState = MutableLiveData<UiState<MealDetailResponse>>()
    val mealDetailRecipeState: LiveData<UiState<MealDetailResponse>> get() = _mealDetailRecipeState

    private val _mealSearchRecipeState = MutableLiveData<UiState<MealDetailResponse>>()
    val mealSearchRecipeState: LiveData<UiState<MealDetailResponse>> get() = _mealSearchRecipeState

    private val _addToFavSuccess = MutableLiveData<Boolean>()
    val addToFavSuccess: LiveData<Boolean> get() = _addToFavSuccess

    private val _getFavouriteMealsSuccess = MutableLiveData<UiState<List<RecipeModelEntity>>>()
    val getFavouriteMealsSuccess: LiveData<UiState<List<RecipeModelEntity>>> get() = _getFavouriteMealsSuccess

    var youtubeUrl: String? = ""
    var sourceUrl: String? = ""

    var recipeModelResponse: MealDetailResponse? = null

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            _recipesState.value = UiState.Loading
            try {
                val response = repo.search(query)
                _recipesState.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _recipesState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getRandomRecipes() {
        viewModelScope.launch {
            _randomRecipeState.value = UiState.Loading
            try {
                val response = repo.getRandomRecipe()
                _randomRecipeState.value = UiState.Success(response)
            } catch (e: Exception) {
                _randomRecipeState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getTrendingRecipes() {
        viewModelScope.launch {
            _trendingRecipeState.value = UiState.Loading
            try {
                val response = repo.getTrendingRecipes()
                _trendingRecipeState.value = UiState.Success(response)
            } catch (e: Exception) {
                _trendingRecipeState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getRecipeDetail(mealId: String) {
        viewModelScope.launch {
            _mealDetailRecipeState.value = UiState.Loading
            try {
                val response = repo.getMealDetails(mealId)
                _mealDetailRecipeState.value = UiState.Success(response)
                recipeModelResponse = response
            } catch (e: Exception) {
                _mealDetailRecipeState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getSearchMeal(searchQuery: String) {
        viewModelScope.launch {
            _mealSearchRecipeState.value = UiState.Loading
            try {
                val response = repo.getSearchMeal(searchQuery)
                _mealSearchRecipeState.value = UiState.Success(response)
            } catch (e: Exception) {
                _mealSearchRecipeState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addRecipeToFavourite(task: RecipeModelEntity) {
        viewModelScope.launch {
            runCatching {
                databaseRepo.insertRecipe(task)
            }.onSuccess {
                _addToFavSuccess.postValue(true)
            }.onFailure {
                _addToFavSuccess.postValue(false)
            }
        }
    }


    fun getFavouriteMeals() {
        viewModelScope.launch {
            _getFavouriteMealsSuccess.value = UiState.Loading
            try {
                val response = databaseRepo.getAllFavRecipes()
                _getFavouriteMealsSuccess.value = UiState.Success(response)
            } catch (e: Exception) {
                _getFavouriteMealsSuccess.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }


}
