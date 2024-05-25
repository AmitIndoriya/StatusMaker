package com.krisu.statusmaker.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.krisu.statusmaker.R
import com.krisu.statusmaker.app.StatusMakerApp
import com.krisu.statusmaker.data.HomeRepository
import com.squareup.picasso.Picasso
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class EditDesignViewModel @Inject constructor
    (
    private val repository: HomeRepository,
    application: Application
) : BaseViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    val bitmapLD = MutableLiveData<Bitmap>()
    fun getBitmapFromUrl(url: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmapLD.value = bitmap
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }
        }

        Picasso.with(context)
            .load(url)
            .into(target)
    }
}