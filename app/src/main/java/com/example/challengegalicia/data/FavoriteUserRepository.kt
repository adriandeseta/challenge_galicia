package com.example.challengegalicia.data

import com.example.challengegalicia.data.dao.FavoriteUserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteUserRepository @Inject constructor(
    private val dao: FavoriteUserDao
) {
    suspend fun addFavorite(user: FavoriteUserEntity) = dao.insert(user)
    fun getFavorites(): Flow<List<FavoriteUserEntity>> = dao.getAll()
}
