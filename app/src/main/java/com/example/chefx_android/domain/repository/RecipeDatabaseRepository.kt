package com.example.chefx_android.domain.repository

import com.example.chefx_android.data.local.RecipeDao
import com.example.chefx_android.data.local.dto.RecipeModelEntity
import javax.inject.Inject


class RecipeDatabaseRepository @Inject constructor(private val dao: RecipeDao) {

    suspend fun insertRecipe(task: RecipeModelEntity) {
        dao.insertFavRecipe(task)
    }

    suspend fun getAllFavRecipes(): List<RecipeModelEntity>{
        return dao.getAllRecipes()
    }
}