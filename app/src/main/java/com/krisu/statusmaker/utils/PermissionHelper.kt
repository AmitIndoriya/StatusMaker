package com.krisu.statusmaker.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper constructor(val activity: Activity){
    val CAMERA_REQUEST = 100
    val STORAGE_REQUEST = 200
    var cameraPermission: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var storagePermission: Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    val storagePermissions33 = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO,
        Manifest.permission.READ_MEDIA_VIDEO
    )
    private val storagePermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun requestCameraPermission() {
        activity.requestPermissions(cameraPermission, CAMERA_REQUEST)
    }

    private fun permissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            storagePermissions33
        } else {
            storagePermissions
        }
    }

    fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkCameraPermission(): Boolean {
        /*  val result1 = ContextCompat.checkSelfPermission(
              context,
              Manifest.permission.WRITE_EXTERNAL_STORAGE
          ) == PackageManager.PERMISSION_GRANTED*/
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            activity,
            permissions(),
            STORAGE_REQUEST
        )
    }
}