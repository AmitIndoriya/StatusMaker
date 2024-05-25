package com.krisu.statusmaker.model

import java.io.Serializable

data class ImageBean(
    val id: String,
    val url: String,
    val categoryId: String,
    val categoryName: String,
    val langCode: String,
    val specificDate: String
) : Serializable
