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
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActHomeLayoutBinding
import com.krisu.statusmaker.model.CategoryBean
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.ui.adapter.CategoryAdapter
import com.krisu.statusmaker.ui.adapter.HomeRVAdapter
import com.krisu.statusmaker.ui.adapter.HomeRVAdapterNew
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
        binding.recyclerview.layoutParams = layoutParams;

    }

    private fun setToolbarMargin() {
        binding.toolbarLl.setPadding(0, getStatusBarHeight(), 0, 0)
    }


    private fun fetchData() {
        fetchImages()
        fetchCategories()
        viewModel.categoryResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        //setCategoryRV(it.data)
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
                        val arrayList1 = ArrayList<ImageBean>()
                        arrayList1.add(
                            ImageBean(
                                id = "1",
                                url = "https://www.astroganit.com/status/khatu_shyam/shyamji1.jpg",
                                categoryId = "1",
                                categoryName = "",
                                langCode = "",
                                specificDate = ""
                            )
                        )
                        arrayList1.add(
                            ImageBean(
                                id = "1",
                                url = "https://www.astroganit.com/status/khatu_shyam/shyamji2.jpg",
                                categoryId = "1",
                                categoryName = "",
                                langCode = "",
                                specificDate = ""
                            )
                        )
                        arrayList1.add(
                            ImageBean(
                                id = "1",
                                url = "https://www.astroganit.com/status/khatu_shyam/shyamji3.jpeg",
                                categoryId = "1",
                                categoryName = "",
                                langCode = "",
                                specificDate = ""
                            )
                        )
                        arrayList1.add(
                            ImageBean(
                                id = "1",
                                url = "https://www.astroganit.com/status/khatu_shyam/shyamji4.jpg",
                                categoryId = "1",
                                categoryName = "",
                                langCode = "",
                                specificDate = ""
                            )
                        )
                        arrayList1.add(
                            ImageBean(
                                id = "1",
                                url = "https://www.astroganit.com/status/khatu_shyam/shyamji5.jpeg",
                                categoryId = "1",
                                categoryName = "",
                                langCode = "",
                                specificDate = ""
                            )
                        )
                        arrayList1.add(
                            ImageBean(
                                id = "1",
                                url = "https://www.astroganit.com/status/khatu_shyam/shyamji6.jpg",
                                categoryId = "1",
                                categoryName = "",
                                langCode = "",
                                specificDate = ""
                            )
                        )
                        arrayList1.add(
                            ImageBean(
                                id = "1",
                                url = "https://www.astroganit.com/status/khatu_shyam/shyamji7.jpeg",
                                categoryId = "1",
                                categoryName = "",
                                langCode = "",
                                specificDate = ""
                            )
                        )

                        notifyAdapter(arrayList1, null)
                        viewModel.getBitmapList(arrayList1)
                    }
                }

                is NetworkResult.Error -> {
                }

                is NetworkResult.Loading -> {

                }
            }
        }
        viewModel.bitmapListLD.observe(this) {
            //adapter.addBitmap(it)
        }
    }

    private fun fetchCategories() {
        viewModel.fetchCategories("2")
    }

    private fun fetchImages() {
        viewModel.fetchImages("2")
    }

    private fun setCategoryRV(arrayList: ArrayList<CategoryBean>) {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.COLUMN;
        layoutManager.justifyContent = JustifyContent.FLEX_END;
        binding.recyclerview.layoutManager = layoutManager
        val adapter = CategoryAdapter(this, arrayList)
        binding.recyclerview.adapter = adapter
    }

    private fun setRVAdapter() {
        val bitmapList = ArrayList<Bitmap>()
        val arrayList = ArrayList<ImageBean>()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager
        adapter = HomeRVAdapterNew(this, arrayList, bitmapList)
        binding.recyclerview.adapter = adapter
    }

    private fun notifyAdapter(arrayList: ArrayList<ImageBean>?, bitmapList: ArrayList<Bitmap>?) {
        adapter.addItem(arrayList, bitmapList)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cat_iv -> {

            }

            R.id.profile_iv -> {
                startActivityForResult(Intent(this, ProfileActivity::class.java), 101)
            }

            R.id.create_tv -> {

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            setProfileData()
            adapter.notifyDataSetChanged()
        }
    }
}