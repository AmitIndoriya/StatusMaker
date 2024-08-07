package com.krisu.statusmaker.viewmodel

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor
    (
    application: Application
) : BaseViewModel(application) {

}