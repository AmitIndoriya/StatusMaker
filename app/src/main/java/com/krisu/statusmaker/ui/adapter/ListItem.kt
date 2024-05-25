package com.krisu.statusmaker.ui.adapter

sealed class ListItem {
    data class Item1(val imgUrl: String) : ListItem()
    data class TextItem(val text: String) : ListItem()
    data class ImageItem(val imageUrl: String) : ListItem()
}