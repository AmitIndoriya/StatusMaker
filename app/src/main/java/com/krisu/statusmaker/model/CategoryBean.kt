package com.krisu.statusmaker.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryBean(
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("categoryName")
    val categoryName: String,
) : Serializable
