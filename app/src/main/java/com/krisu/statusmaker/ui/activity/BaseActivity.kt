package com.krisu.statusmaker.ui.activity

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.krisu.statusmaker.R
import com.krisu.statusmaker.utils.CacheStore
import com.krisu.statusmaker.utils.Constants
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

open class BaseActivity : AppCompatActivity() {

    fun setFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    fun getNavigationBarHeight(): Int {
        var navigationBarHeight = 0
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return navigationBarHeight
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun viewToImage(view: View, shareText: String): Bitmap? {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        try {
            val cachePath = File(cacheDir, "images")
            cachePath.mkdirs() // don't forget to make the directory
            val stream =
                FileOutputStream("$cachePath/image.png") // overwrites this image every time
            returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
            shareImage(true, shareText)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return returnedBitmap
    }

    private fun viewToImage(view: View): Bitmap? {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        try {
            val cachePath = File(cacheDir, "images")
            cachePath.mkdirs()
            val stream = FileOutputStream("$cachePath/image.png")
            returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return returnedBitmap
    }

    fun shareImage(isShowCaption: Boolean, shareText: String) {
        val imagePath = File(cacheDir, "images")
        val newFile = File(imagePath, "image.png")
        val contentUri =
            FileProvider.getUriForFile(
                this,
                "com.krisu.statusmaker.provider",
                newFile
            )

        if (contentUri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
            shareIntent.setDataAndType(
                contentUri,
                contentResolver.getType(contentUri)
            )
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            /*if (isShowCaption) {
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    shareText + "\n" + GlobalVariables.APP_URL
                )
            }*/
            startActivity(Intent.createChooser(shareIntent, "Choose an app"))
        }
    }

    fun saveImageInCache(view: View) {
        val bitmap = viewToImage(view)
        CacheStore.getInstance().saveCacheFile("bitmap", bitmap)
    }

    fun getImageFromCache(): Bitmap {
        return CacheStore.getInstance().getCacheFile("bitmap")
    }

    fun setStatusBarColor(color: Int) {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(color, null)
        setSystemNavigationColor()
    }


    fun setSystemNavigationColor() {
        val window: Window = this.window
        window.navigationBarColor = this.resources.getColor(R.color.white)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }

    fun shareApp() {
        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.type = "text/plain"
        whatsappIntent.setPackage("com.whatsapp")
        val shareText =
            "अपने दोस्त और परिवार संग शेयर करे मोटिवेशनल, बधाई और सुविचार संदेश " + Constants.APP_URL
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        try {
            startActivity(whatsappIntent)
        } catch (_: ActivityNotFoundException) {
        }
    }
}