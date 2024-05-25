package com.krisu.statusmaker.model

import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("categoryName")
    val categoryName: String
)