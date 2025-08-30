package com.example.challengegalicia.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.challengegalicia.data.local.FavoriteUserEntity
import com.example.challengegalicia.data.dao.FavoriteUserDao

@Database(
    entities = [FavoriteUserEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}
