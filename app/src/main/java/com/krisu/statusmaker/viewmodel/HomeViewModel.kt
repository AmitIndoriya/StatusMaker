package com.krisu.statusmaker.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.krisu.statusmaker.data.HomeRepository
import com.krisu.statusmaker.model.CategoryResponse
import com.krisu.statusmaker.model.GetAllmagesResponse
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.model.ImageBitmapBean
import com.krisu.statusmaker.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor
    (
    private val repository: HomeRepository,
    application: Application
) : BaseViewModel(application) {

    val categoryResponse: MutableLiveData<NetworkResult<CategoryResponse>> = MutableLiveData()
    val allImageResponse: MutableLiveData<NetworkResult<GetAllmagesResponse>> = MutableLiveData()
    val catImageResponse: MutableLiveData<NetworkResult<GetAllmagesResponse>> = MutableLiveData()


    fun fetchCategories(langCode: String) = viewModelScope.launch {
        categoryResponse.value = NetworkResult.Loading()
        repository.getCategories(langCode).collect { values ->
            categoryResponse.value = values
        }
    }

    fun fetchCatAndImgTogether(page: Int, size: Int, time: Long, langCode: String) {
        viewModelScope.launch {
            categoryResponse.value = NetworkResult.Loading()
            // _movieTitle.postValue(DataState.Init)
            val resultA = async { repository.getCategories(langCode) }
            val resultB = async { repository.getAllImages(page, size, time) }
            val result = awaitAll(resultA, resultB)

            (result[1] as Flow<NetworkResult<GetAllmagesResponse>>).collect { values ->
                allImageResponse.value = values
            }
            (result[0] as Flow<NetworkResult<CategoryResponse>>).collect { values ->
                categoryResponse.value = values
            }
        }
    }

    fun fetchImages(langCode: String) = viewModelScope.launch {
        repository.getAllImages(langCode).collect { values ->
            allImageResponse.value = values
        }
    }

    fun fetchImages(page: Int, size: Int, time: Long) = viewModelScope.launch {
        allImageResponse.value = NetworkResult.Loading()
        repository.getAllImages(page, size, time).collect { values ->
            allImageResponse.value = values
        }
    }

    fun fetchImagesByCatId(id: String, langCode: String) = viewModelScope.launch {
        catImageResponse.value = NetworkResult.Loading()
        repository.getImagesByCatId(id, langCode).collect { values ->
            catImageResponse.value = values
        }
    }

    fun fetchImagesBySubCatId(id: String, langCode: String) = viewModelScope.launch {
        catImageResponse.value = NetworkResult.Loading()
        repository.getImagesBySubCatId(id, langCode).collect { values ->
            catImageResponse.value = values
        }
    }

}