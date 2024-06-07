package com.krisu.statusmaker.ui.activity

import android.os.Bundle
import com.krisu.statusmaker.databinding.ActShareStatusLayoutBinding
import com.krisu.statusmaker.utils.Utils
import com.squareup.picasso.Picasso

class ShareStatusActivity : BaseActivity() {
    lateinit var binding: ActShareStatusLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActShareStatusLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        //setStatusBarColor(R.color.white)
        setListeners()
    }

    fun getIntentData() {
        binding.imageView.setImageBitmap(getImageFromCache())
        Picasso.with(this).load(Utils.getStringInSP(this, "img_url")).into(binding.profileImg)
    }

    fun shareStatus() {

        viewToImage(binding.container, "")
        //shareImage(false)
    }

    fun setListeners() {
        binding.shareTv.setOnClickListener {
            shareStatus()
        }

    }
}