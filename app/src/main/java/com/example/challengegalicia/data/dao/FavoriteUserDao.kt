package com.example.challengegalicia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.challengegalicia.data.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: FavoriteUserEntity)

    @Query("SELECT * FROM favorite_users")
    fun getAll(): Flow<List<FavoriteUserEntity>>
}
