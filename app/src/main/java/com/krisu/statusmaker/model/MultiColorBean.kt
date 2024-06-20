package com.krisu.statusmaker.model

import java.io.Serializable

data class MultiColorBean(
    val id: Int = 0,
    var start: Int,
    var end: Int,
    var textColor: Int,
    var shadowColor: Int
) : Serializable
