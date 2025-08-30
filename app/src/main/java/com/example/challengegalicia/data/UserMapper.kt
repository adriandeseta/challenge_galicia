package com.example.challengegalicia.data

import com.example.challengegalicia.data.local.FavoriteUserEntity
import com.example.challengegalicia.presentation.model.UserModel

fun UserModel.toFavoriteUserEntity(): FavoriteUserEntity {
    return FavoriteUserEntity(
        uuid = this.uuid,
        email = this.email,
        firstName = this.name.firstName,
        lastName = this.name.lastName,
        country = this.country,
        pictureUrl = this.picture.large,
        age = this.dob.age.toString(),
        phone = this.phone
    )
}
