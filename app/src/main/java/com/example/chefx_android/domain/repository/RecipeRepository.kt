package com.example.chefx_android.domain.repository

import com.example.chefx_android.data.remote.ApiService

class RecipeRepository(
    private val api: ApiService
) {
    suspend fun search(query: String) = api.searchRecipes(query)
    suspend fun getRandomRecipe() = api.getRandomRecipes()
    suspend fun getTrendingRecipes() = api.getTrendingRecipes()
    suspend fun getMealDetails(mealId: String) = api.getMealDetails(mealId)
    suspend fun getSearchMeal(searchQuery: String) = api.searchMeals(searchQuery)
}
