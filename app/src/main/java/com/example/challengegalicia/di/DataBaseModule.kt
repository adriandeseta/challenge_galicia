package com.example.challengegalicia.di

import android.content.Context
import androidx.room.Room
import com.example.challengegalicia.data.dao.FavoriteUserDao
import com.example.challengegalicia.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "challenge_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideFavoriteUserDao(database: AppDatabase): FavoriteUserDao {
        return database.favoriteUserDao()
    }
}
