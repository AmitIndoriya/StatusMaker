package com.krisu.statusmaker.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryBean(
    @SerializedName("id")
    var id: Int,
    @SerializedName("cateId")
    var categoryId: Int,
    @SerializedName("cateName")
    var categoryName: String,
    @SerializedName("parentCateId")
    var parentCateId: Int,
    @SerializedName("parentCateName")
    var parentCateName: String,
    @SerializedName("lang")
    var lang: String = "2",
) : Serializable
