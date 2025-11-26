package com.example.chefx_android.data.remote.dto

import com.example.chefx_android.data.local.dto.RecipeModelEntity

data class TrendingRecipesResponse(
    val meals: List<MealItem>
)

data class MealItem(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String,
    val isFavMeal: Boolean = false
)

fun MealDetail.getIngredientList(): List<IngredientResponse> {
    val list = mutableListOf<IngredientResponse>()

    for (i in 1..20) {
        val ing = this.javaClass.getDeclaredField("strIngredient$i")
            .apply { isAccessible = true }
            .get(this) as? String

        val measure = this.javaClass.getDeclaredField("strMeasure$i")
            .apply { isAccessible = true }
            .get(this) as? String

        if (!ing.isNullOrBlank()) {
            list.add(IngredientResponse(ing, measure ?: ""))
        }
    }
    return list
}

fun RecipeModelEntity.toMealItem(): MealItem {
    return MealItem(
        strMeal = this.strMeal ?: "",
        strMealThumb = this.strMealThumb ?: "",
        idMeal = this.idMeal
    )
}

fun List<RecipeModelEntity>.toMealItemList(): List<MealItem> {
    return this.map { it.toMealItem() }
}


