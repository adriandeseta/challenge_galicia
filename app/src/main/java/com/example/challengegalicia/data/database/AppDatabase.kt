package com.example.challengegalicia.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.challengegalicia.data.dao.FavoriteUserDao

@Database(entities = [FavoriteUserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}
