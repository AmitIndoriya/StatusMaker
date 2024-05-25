package com.krisu.statusmaker.data.remote


import com.krisu.statusmaker.model.CategoryResponse
import com.krisu.statusmaker.model.DogResponse
import com.krisu.statusmaker.model.GetAllmagesResponse
import com.krisu.statusmaker.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Constants.GETCATEGORIES)
    suspend fun getCategories(@Path("langCode") langCode: String): Response<CategoryResponse>

    @GET(Constants.GETALLIMAGES)
    suspend fun getAllImages(@Path("langCode") langCode: String): Response<GetAllmagesResponse>
}