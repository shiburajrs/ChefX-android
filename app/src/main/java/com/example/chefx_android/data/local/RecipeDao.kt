package com.example.chefx_android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chefx_android.data.local.dto.RecipeModelEntity

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavRecipe(task: RecipeModelEntity)

    @Query("SELECT * FROM favouriteRecipes ORDER BY id DESC")
    suspend fun getAllRecipes(): List<RecipeModelEntity>
}