package com.example.challengegalicia.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
data class FavoriteUserEntity(
    @PrimaryKey val uuid: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val country: String?,
    val pictureUrl: String,
    val age: String,
    val phone: String
)
