package com.krisu.statusmaker.utils

class Constants {

    companion object {
        const val APP_URL = "https://play.google.com/store/apps/details?id=com.krisu.statusmaker"
        const val BASE_URL = "http://64.227.152.37:5000/api/"
        const val GETALLIMAGES = "imagecat/image/getallimage/{langCode}"
        const val GETIMAGESBYCATID = "imagecat/image/imagebycategory/{catId}/{langCode}"
        const val GETCATEGORIES = "imagecat/image/getallcategory/{langCode}"
    }
}