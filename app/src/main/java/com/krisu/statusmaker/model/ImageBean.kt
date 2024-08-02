package com.krisu.statusmaker.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImageBean(
    @SerializedName("id")
    var id: String?=null,
    @SerializedName("imgUrl")
    val url: String?=null,
    @SerializedName("cateId")
    var categoryId: Int?=null,
    @SerializedName("cateName")
    val categoryName: String?=null,
    @SerializedName("lang")
    var langCode: String="2",
    @SerializedName("subCateId")
    var subCategoryId: Int?=null,
    @SerializedName("subCateName")
    val subCategoryName: String?=null,
) : Serializable
