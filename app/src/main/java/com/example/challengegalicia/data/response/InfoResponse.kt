package com.example.challengegalicia.data.response
import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("seed") val seed: String,
    @SerializedName("result") val result: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("version") val version: String,
)
