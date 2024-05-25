package com.krisu.statusmaker.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetAllmagesResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("resultCode")
    val resultCode: Int,
    @SerializedName("errorMessage")
    val errorMessage: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: ArrayList<ImageBean>

) : Serializable
