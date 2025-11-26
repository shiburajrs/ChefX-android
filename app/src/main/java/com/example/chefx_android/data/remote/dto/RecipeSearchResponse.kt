package com.example.chefx_android.data.remote.dto

data class RecipeSearchResponse(
    val results: List<RecipeItem>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)

data class RecipeItem(
    val id: Int,
    val title: String,
    val image: String
)