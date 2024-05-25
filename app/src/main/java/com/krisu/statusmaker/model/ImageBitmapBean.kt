package com.krisu.statusmaker.model

import android.graphics.Bitmap
import java.io.Serializable

data class ImageBitmapBean(
    val url: String, val bitmap: Bitmap
) : Serializable
