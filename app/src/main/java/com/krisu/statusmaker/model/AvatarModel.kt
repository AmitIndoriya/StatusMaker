package com.krisu.statusmaker.model

import android.graphics.drawable.Drawable
import java.io.Serializable

data class AvatarModel(
    val id: Int,
    val drawable: Drawable
) : Serializable
