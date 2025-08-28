package com.example.challengegalicia.data.response

import com.example.challengegalicia.presentation.model.UserModel
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: Name,
    @SerializedName("picture") val picture: UserImage,
    @SerializedName("country") val country: String,
    @SerializedName("email") val email: String,
    @SerializedName("dob") val dob: Dob,
    @SerializedName("location") val location: UserLocation,
    @SerializedName("phone") val phone: String,
){
    fun toPresentation():UserModel{
        return UserModel(
            gender = gender,
            name = name,
            picture = picture,
            country = country,
            email = email,
            dob = dob,
            location = location,
            phone = phone
        )
    }
}

data class Name(
    @SerializedName("title") val title: String,
    @SerializedName("first") val firstName: String,
    @SerializedName("last") val lastName: String,
)

data class UserImage(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("thumbnail") val thumbnail: String,
)

data class Dob(
    @SerializedName("date") val date: String,
    @SerializedName("age") val age: Int,
)

data class UserLocation(
    @SerializedName("street") val street: Street,
)

data class Street(
    @SerializedName("number") val number: Int,
    @SerializedName("name") val name: String,
)