package com.krisu.statusmaker.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.AvatarModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    val context = application
    val avatarListLD = MutableLiveData<HashMap<Int, AvatarModel>>()

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getAvatarList() {
        val hashMap = HashMap<Int, AvatarModel>()
        hashMap[1] = (AvatarModel(1, context.resources.getDrawable(R.drawable.avatar1)))
        hashMap[2] = (AvatarModel(2, context.resources.getDrawable(R.drawable.avatar2)))
        hashMap[3] = (AvatarModel(3, context.resources.getDrawable(R.drawable.avatar3)))
        hashMap[4] = (AvatarModel(4, context.resources.getDrawable(R.drawable.avatar4)))
        hashMap[5] = (AvatarModel(5, context.resources.getDrawable(R.drawable.avatar5)))
        hashMap[6] = (AvatarModel(6, context.resources.getDrawable(R.drawable.avatar6)))
        hashMap[7] = (AvatarModel(7, context.resources.getDrawable(R.drawable.avatar7)))
        hashMap[8] = (AvatarModel(8, context.resources.getDrawable(R.drawable.avatar8)))
        hashMap[9] = (AvatarModel(9, context.resources.getDrawable(R.drawable.avatar9)))
        hashMap[10] = (AvatarModel(10, context.resources.getDrawable(R.drawable.avatar10)))
        hashMap[11] = (AvatarModel(11, context.resources.getDrawable(R.drawable.avatar11)))
        hashMap[12] = (AvatarModel(12, context.resources.getDrawable(R.drawable.avatar12)))
        hashMap[13] = (AvatarModel(13, context.resources.getDrawable(R.drawable.avatar13)))
        hashMap[14] = (AvatarModel(14, context.resources.getDrawable(R.drawable.avatar14)))
        hashMap[15] = (AvatarModel(15, context.resources.getDrawable(R.drawable.avatar15)))
        hashMap[16] = (AvatarModel(16, context.resources.getDrawable(R.drawable.avatar16)))
        hashMap[17] = (AvatarModel(17, context.resources.getDrawable(R.drawable.avatar17)))
        hashMap[18] = (AvatarModel(18, context.resources.getDrawable(R.drawable.avatar18)))
        hashMap[19] = (AvatarModel(19, context.resources.getDrawable(R.drawable.avatar19)))
        hashMap[20] = (AvatarModel(20, context.resources.getDrawable(R.drawable.avatar20)))
        hashMap[21] = (AvatarModel(21, context.resources.getDrawable(R.drawable.avatar21)))
        hashMap[22] = (AvatarModel(22, context.resources.getDrawable(R.drawable.avatar22)))
        hashMap[23] = (AvatarModel(23, context.resources.getDrawable(R.drawable.avatar23)))
        hashMap[24] = (AvatarModel(24, context.resources.getDrawable(R.drawable.avatar24)))
        hashMap[25] = (AvatarModel(25, context.resources.getDrawable(R.drawable.avatar25)))
        hashMap[26] = (AvatarModel(26, context.resources.getDrawable(R.drawable.avatar26)))
        hashMap[27] = (AvatarModel(27, context.resources.getDrawable(R.drawable.avatar27)))
        hashMap[28] = (AvatarModel(28, context.resources.getDrawable(R.drawable.avatar28)))
        hashMap[29] = (AvatarModel(29, context.resources.getDrawable(R.drawable.avatar29)))
        hashMap[30] = (AvatarModel(30, context.resources.getDrawable(R.drawable.avatar30)))
        hashMap[31] = (AvatarModel(31, context.resources.getDrawable(R.drawable.avatar31)))
        hashMap[32] = (AvatarModel(32, context.resources.getDrawable(R.drawable.avatar32)))
        hashMap[33] = (AvatarModel(33, context.resources.getDrawable(R.drawable.avatar33)))
        hashMap[34] = (AvatarModel(34, context.resources.getDrawable(R.drawable.avatar34)))
        hashMap[35] = (AvatarModel(35, context.resources.getDrawable(R.drawable.avatar35)))
        hashMap[36] = (AvatarModel(36, context.resources.getDrawable(R.drawable.avatar36)))
        hashMap[37] = (AvatarModel(37, context.resources.getDrawable(R.drawable.avatar37)))
        hashMap[38] = (AvatarModel(38, context.resources.getDrawable(R.drawable.avatar38)))
        hashMap[39] = (AvatarModel(39, context.resources.getDrawable(R.drawable.avatar39)))
        hashMap[40] = (AvatarModel(40, context.resources.getDrawable(R.drawable.avatar40)))

        avatarListLD.value = hashMap
    }
}