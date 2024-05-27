package com.krisu.statusmaker.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActHomeLayoutBinding
import com.krisu.statusmaker.model.CategoryBean
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.ui.adapter.HomeRVAdapterNew
import com.krisu.statusmaker.ui.dialog.CategoryBottomSheet
import com.krisu.statusmaker.utils.NetworkResult
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.krisu.statusmaker.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity(), OnClickListener {
    lateinit var binding: ActHomeLayoutBinding
    val viewModel by viewModels<HomeViewModel>()
    lateinit var adapter: HomeRVAdapterNew
    var selectedCategory = 0
    private lateinit var categoryBottomSheet: CategoryBottomSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        binding = ActHomeLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbarMargin()
        fetchData()
        setRVAdapter()
        setListeners()
        setProfileData()
        setBottomMargin()
        addObservers()
    }

    private fun setListeners() {
        binding.catIv.setOnClickListener(this)
        binding.profileIv.setOnClickListener(this)
        binding.createTv.setOnClickListener(this)
    }

    private fun addObservers() {
        viewModel.avatarListLD.observe(this) {
            val avatarId = Utils.getIntInSP(this, PreferenceConstant.AVATAR_ID)
            if (avatarId != -1) {
                binding.profileIv.setImageDrawable(it[avatarId]?.drawable)
            }
        }
    }

    private fun setProfileData() {
        if (Utils.getBooleanInSP(this, PreferenceConstant.IS_AVATAR_SELECTED)) {
            viewModel.getAvatarList()
        } else {
            if (!TextUtils.isEmpty(Utils.getStringInSP(this, PreferenceConstant.PROFILE_IMG))) {
                Picasso.with(this).load(Utils.getStringInSP(this, PreferenceConstant.PROFILE_IMG))
                    .into(binding.profileIv)
            }
        }
    }

    private fun setBottomMargin() {
        val layoutParams = binding.recyclerview.layoutParams as RelativeLayout.LayoutParams
        layoutParams.bottomMargin = getNavigationBarHeight()
        binding.recyclerview.layoutParams = layoutParams

    }

    private fun setToolbarMargin() {
        binding.toolbarLl.setPadding(0, getStatusBarHeight(), 0, 0)
    }


    private fun fetchData() {
        fetchImages()
        viewModel.categoryResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let {
                        it.data.add(0, CategoryBean("0", "सभी"))
                        categoryBottomSheet = CategoryBottomSheet.getInstance(it.data)
                        categoryBottomSheet.show(supportFragmentManager, "categoryBottomSheet")
                    }
                }

                is NetworkResult.Error -> {
                }

                is NetworkResult.Loading -> {
                }
            }
        }
        viewModel.allImageResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        addItem(it.data, null)
                    }
                }

                is NetworkResult.Error -> {
                }

                is NetworkResult.Loading -> {

                }
            }
        }
        viewModel.catImageResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        replaceItem(it.data)
                    }
                }

                is NetworkResult.Error -> {
                }

                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun fetchCategories() {
        viewModel.fetchCategories("2")
    }

    private fun fetchImages() {
        viewModel.fetchImages("2")
    }

    fun fetchImagesById(id: String) {
        if (id == "0") {
            fetchImages()
        } else {
            viewModel.fetchImagesByCatId(id, "2")
        }
        categoryBottomSheet.dismiss()
    }

    private fun setRVAdapter() {
        val bitmapList = ArrayList<Bitmap>()
        val arrayList = ArrayList<ImageBean>()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager
        adapter = HomeRVAdapterNew(this, arrayList, bitmapList)
        binding.recyclerview.adapter = adapter
    }

    private fun addItem(arrayList: ArrayList<ImageBean>?, bitmapList: ArrayList<Bitmap>?) {
        adapter.addItem(arrayList, bitmapList)
    }

    private fun replaceItem(arrayList: ArrayList<ImageBean>?) {
        adapter.replaceItem(arrayList)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cat_iv -> {
                fetchCategories()
            }

            R.id.profile_iv -> {
                startActivityForResult(Intent(this, ProfileActivity::class.java), 101)
            }

            R.id.create_tv -> {

            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            setProfileData()
            adapter.notifyDataSetChanged()
        }
    }
}