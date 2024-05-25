package com.krisu.statusmaker.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getCategories(langCode: String) =
        apiService.getCategories(langCode = langCode)

    suspend fun getAllImages(langCode: String) =
        apiService.getAllImages(langCode = langCode)

}