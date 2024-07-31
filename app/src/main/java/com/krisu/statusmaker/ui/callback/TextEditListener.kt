package com.krisu.statusmaker.ui.callback

interface TextEditListener {
    fun changeTextColor(color: Int)
    fun changeStrokeColor(color: Int)
    fun changeStrokeWidht(width: Float)
    fun changeAlignment(type: Int)
}