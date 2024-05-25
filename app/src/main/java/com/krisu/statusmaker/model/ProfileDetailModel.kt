package com.krisu.statusmaker.model

import retrofit2.http.Url
import java.io.Serializable

data class ProfileDetailModel(
    var name: String = "",
    var mobileNumber: String = "",
    var imageUrl: String = ""
) : Serializable
