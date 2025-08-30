package com.example.challengegalicia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.challengegalicia.data.local.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: FavoriteUserEntity)

    @Query("SELECT * FROM favorite_users")
    fun getAll(): Flow<List<FavoriteUserEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_users WHERE uuid = :uuid)")
    fun isFavorite(uuid: String): Flow<Boolean>

    @Query("DELETE FROM favorite_users WHERE uuid = :uuid")
    suspend fun deleteByUuid(uuid: String)
}
