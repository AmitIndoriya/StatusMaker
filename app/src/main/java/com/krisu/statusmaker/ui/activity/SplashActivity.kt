package com.krisu.statusmaker.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActSplashLayoutBinding
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.krisu.statusmaker.viewmodel.SplashActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    lateinit var binding: ActSplashLayoutBinding

    private fun attachViewModel() {
        if (!TextUtils.isEmpty(Utils.getStringInSP(this, PreferenceConstant.PROFILE_NAME))) {
            Handler().postDelayed({
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        } else {
            Handler().postDelayed({
                val intent = Intent(this@SplashActivity, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActSplashLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animateImageView()
        attachViewModel()
        setStatusBarColor(R.color.white)
    }

    private fun animateImageView() {
        val aniSlide: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        binding.logoIv.startAnimation(aniSlide)
    }
}