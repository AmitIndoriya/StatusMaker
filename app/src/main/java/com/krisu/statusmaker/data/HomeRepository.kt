package com.krisu.statusmaker.data


import android.util.Log
import com.krisu.statusmaker.data.remote.RemoteDataSource
import com.krisu.statusmaker.model.BaseApiResponse
import com.krisu.statusmaker.model.CategoryResponse
import com.krisu.statusmaker.model.DogResponse
import com.krisu.statusmaker.model.GetAllmagesResponse
import com.krisu.statusmaker.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getCategories(langCode: String): Flow<NetworkResult<CategoryResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getCategories(langCode) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllImages(langCode: String): Flow<NetworkResult<GetAllmagesResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getAllImages(langCode) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getAllImages(page: Int,  size: Int): Flow<NetworkResult<GetAllmagesResponse>> {
        return flow {
            emit(safeApiCall {
                remoteDataSource.getAllImages(page,size) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getImagesByCatId(
        id: String,
        langCode: String
    ): Flow<NetworkResult<GetAllmagesResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getImagesByCatId(id, langCode) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getImagesBySubCatId(
        id: String,
        langCode: String
    ): Flow<NetworkResult<GetAllmagesResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getImagesBySubCatId(id, langCode) })
        }.flowOn(Dispatchers.IO)
    }
}