package com.example.challengegalicia.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
data class FavoriteUserEntity(
    @PrimaryKey val email: String, // asumimos email único
    val firstName: String,
    val lastName: String,
    val country: String?,
    val pictureUrl: String
)
