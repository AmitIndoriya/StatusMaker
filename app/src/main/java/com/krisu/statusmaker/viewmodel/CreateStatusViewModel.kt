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
    val imagesUrl = arrayOf(
        "https://www.astroganit.com/astroganit_images/status_background/bg1.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg2.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg3.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg4.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg5.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg6.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg8.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg9.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg10.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg11.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg12.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg13.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg14.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg15.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg16.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg17.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg18.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg19.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg20.jpg",
        "https://www.astroganit.com/astroganit_images/status_background/bg21.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg22.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg26.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg27.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg28.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg29.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg30.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg31.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg32.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg33.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg34.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg35.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg36.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg37.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg38.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg39.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg40.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg41.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg42.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg43.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg44.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg45.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg46.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg47.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg48.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg49.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg50.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg51.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg52.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg53.jpeg",
        "https://www.astroganit.com/astroganit_images/status_background/bg54.jpeg",
    )
    private val categoryResponse: MutableLiveData<NetworkResult<CategoryResponse>> =
        MutableLiveData()
    val allImageResponse: MutableLiveData<NetworkResult<GetAllmagesResponse>> = MutableLiveData()
    private val catImageResponse: MutableLiveData<NetworkResult<GetAllmagesResponse>> =
        MutableLiveData()
    val imageListLD: MutableLiveData<ArrayList<ImageBean>> = MutableLiveData()

    fun
            fetchCategories(langCode: String) = viewModelScope.launch {
        repository.getCategories(langCode).collect { values ->
            categoryResponse.value = values
        }
    }

    fun fetchImages() = viewModelScope.launch {
        val arrayList = ArrayList<ImageBean>()
        for (i in imagesUrl.indices) {
            arrayList.add(
                ImageBean(
                    id = "1",
                    url = imagesUrl[i],
                    categoryId = "",
                    categoryName = "",
                    langCode = "",
                    specificDate = ""
                )
            )
        }

        imageListLD.value = arrayList
    }

    fun fetchImagesByCatId(id: String, langCode: String) = viewModelScope.launch {
        repository.getImagesByCatId(id, langCode).collect { values ->
            catImageResponse.value = values
        }
    }


}