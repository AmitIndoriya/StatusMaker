package com.krisu.statusmaker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.krisu.statusmaker.data.HomeRepository
import com.krisu.statusmaker.model.CategoryResponse
import com.krisu.statusmaker.model.GetAllmagesResponse
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateStatusViewModel @Inject constructor
    (
    private val repository: HomeRepository,
    application: Application
) : BaseViewModel(application) {

    private val categoryResponse: MutableLiveData<NetworkResult<CategoryResponse>> = MutableLiveData()
    val allImageResponse: MutableLiveData<NetworkResult<GetAllmagesResponse>> = MutableLiveData()
    private val catImageResponse: MutableLiveData<NetworkResult<GetAllmagesResponse>> = MutableLiveData()
    val imageListLD: MutableLiveData<ArrayList<ImageBean>> = MutableLiveData()

    fun fetchCategories(langCode: String) = viewModelScope.launch {
        repository.getCategories(langCode).collect { values ->
            categoryResponse.value = values
        }
    }

    fun fetchImages() = viewModelScope.launch {
        val arrayList = ArrayList<ImageBean>()
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg1.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg2.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg3.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg4.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg5.jpeg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg6.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg8.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg9.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg10.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg11.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg12.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg13.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg14.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg15.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg16.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg17.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg18.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg19.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg20.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg21.jpeg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg22.jpeg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        arrayList.add(
            ImageBean(
                id = "1",
                url = "https://www.astroganit.com/astroganit_images/status_background/bg23.jpg",
                categoryId = "",
                categoryName = "",
                langCode = "",
                specificDate = ""
            )
        )
        imageListLD.value = arrayList
    }

    fun fetchImagesByCatId(id: String, langCode: String) = viewModelScope.launch {
        repository.getImagesByCatId(id, langCode).collect { values ->
            catImageResponse.value = values
        }
    }


}