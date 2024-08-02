package com.krisu.statusmaker.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getCategories(langCode: String) =
        apiService.getCategories(langCode = langCode)

    suspend fun getAllImages(langCode: String) =
        apiService.getAllImages(langCode = langCode)

    suspend fun getImagesByCatId(id: String, langCode: String) =
        apiService.getImagesByCatId(catId = id, langCode = langCode)

    suspend fun getImagesBySubCatId(id: String, langCode: String) =
        apiService.getImagesBySubCatId(catId = id, langCode = langCode)

    suspend fun getAllImages(page: Int, size: Int) =
        apiService.getAllImages(page = page, size = size)

}