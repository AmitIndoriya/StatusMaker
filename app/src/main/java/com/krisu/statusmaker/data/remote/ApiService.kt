package com.krisu.statusmaker.data.remote


import android.media.Image
import com.krisu.statusmaker.model.CategoryResponse
import com.krisu.statusmaker.model.GetAllmagesResponse
import com.krisu.statusmaker.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET(Constants.GETCATEGORIES)
    suspend fun getCategories(@Query("lang") langCode: String): Response<CategoryResponse>

    @GET(Constants.GETALLIMAGES)
    suspend fun getAllImages(@Path("langCode") langCode: String): Response<GetAllmagesResponse>

    @GET(Constants.GETIMAGESBYCATID)
    suspend fun getImagesByCatId(
        @Query("cateId") catId: String,
        @Query("langCode") langCode: String
    ): Response<GetAllmagesResponse>

    @GET(Constants.GETIMAGESBYSUBCATID)
    suspend fun getImagesBySubCatId(
        @Query("subcateId") catId: String,
        @Query("langCode") langCode: String
    ): Response<GetAllmagesResponse>

    @GET(Constants.GETCATEGORIESNEW)
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetAllmagesResponse>
}