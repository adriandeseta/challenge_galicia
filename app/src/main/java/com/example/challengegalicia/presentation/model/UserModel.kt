package com.example.challengegalicia.presentation.model

import com.example.challengegalicia.data.response.Dob
import com.example.challengegalicia.data.response.Name
import com.example.challengegalicia.data.response.UserImage
import com.example.challengegalicia.data.response.UserLocation

data class UserModel(
    val uuid: String,
    val gender: String,
    val name: Name,
    val picture: UserImage,
    val country: String?,
    val email: String,
    val dob: Dob,
    val location: UserLocation,
    val phone: String,
)
