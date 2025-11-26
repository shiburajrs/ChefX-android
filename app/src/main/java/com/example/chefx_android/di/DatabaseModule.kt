package com.example.chefx_android.di

import android.content.Context
import androidx.room.Room
import com.example.chefx_android.data.local.RecipeDao
import com.example.chefx_android.data.local.RecipeDatabase
import com.example.chefx_android.domain.repository.RecipeDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipeDatabase =
        Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_database"
        ).build()

    @Provides
    fun provideDao(database: RecipeDatabase): RecipeDao = database.recipeDao()

    @Provides
    @Singleton
    fun provideRepository(dao: RecipeDao): RecipeDatabaseRepository = RecipeDatabaseRepository(dao)
}