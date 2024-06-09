package com.krisu.statusmaker.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.FragCreateStatusLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.ViewPagerAdapter
import com.krisu.statusmaker.ui.callback.TextEditListener
import com.squareup.picasso.Picasso

class CreateStatusFragment : BaseFragment() {
    lateinit var activity: CreateStatusActivity
    lateinit var binding: FragCreateStatusLayoutBinding


    companion object {
        fun getInstance() = CreateStatusFragment()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    private fun addObservers() {
        activity.bgUrlLD.observe(activity) {
            changeBackground(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragCreateStatusLayoutBinding.inflate(inflater, container, false)
        addObservers()
        setupEdittext()
        Toast.makeText(activity, "oncreateview", Toast.LENGTH_SHORT).show()
        activity.changeToolbar(resources.getString(R.string.create_status), 1)
        return binding.root
    }

    private fun setupEdittext() {

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Handler().postDelayed({
                    activity.suvicharStr = s.toString()
                }, 10)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    fun saveImageInCache() {
        activity.saveImageInCache(binding.imageContainer)
    }

    private fun changeBackground(url: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                binding.imageView.setImageBitmap(bitmap)
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                binding.imageView.setImageResource(R.drawable.default_suvichar_bg)
            }
        }
        Picasso.with(context)
            .load(url)
            .into(target)
    }
}