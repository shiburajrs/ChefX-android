package com.example.chefx_android.data.remote

import com.example.chefx_android.data.remote.dto.MealDetailResponse
import com.example.chefx_android.data.remote.dto.RandomRecipeResponse
import com.example.chefx_android.data.remote.dto.RecipeSearchResponse
import com.example.chefx_android.data.remote.dto.TrendingRecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("number") number: Int = 10
    ): RecipeSearchResponse

    @GET("random.php")
    suspend fun getRandomRecipes(): RandomRecipeResponse

    @GET("filter.php?a=Indian")
    suspend fun getTrendingRecipes(
    ): TrendingRecipesResponse

    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") mealId: String
    ): MealDetailResponse

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealDetailResponse

}
