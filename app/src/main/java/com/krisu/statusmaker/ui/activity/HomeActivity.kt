package com.krisu.statusmaker.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActHomeLayoutBinding
import com.krisu.statusmaker.model.CategoryBean
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.ui.adapter.HomeRVAdapterNew
import com.krisu.statusmaker.ui.dialog.CategoryBottomSheet
import com.krisu.statusmaker.utils.NetworkResult
import com.krisu.statusmaker.utils.PaginationScrollListener
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
    private val PAGE_START = 0
    private var isLoadingPage = false
    private var isLastPage1 = false
    private val TOTAL_PAGES = 10
    private var currentPage = PAGE_START
    val layoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        binding = ActHomeLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbarMargin()
        fetchData()
        setListeners()
        setProfileData()
        setBottomMargin()
        addObservers()
    }

    private fun setListeners() {
        binding.catIv.setOnClickListener(this)
        binding.profileIv.setOnClickListener(this)
        binding.createTv.setOnClickListener(this)
        binding.shareAppIv.setOnClickListener(this)

        binding.recyclerview.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (!isLastPage1 && !isLoadingPage && selectedCategory == 0) {
                    isLoadingPage = true
                    Handler().postDelayed({
                        currentPage++
                        fetchImages(currentPage)

                    }, 1000)
                }
            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLastPage(): Boolean {
                return isLastPage1
            }

            override fun isLoading(): Boolean {
                return isLoadingPage
            }

        })
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
        fetchImages(currentPage)
        viewModel.categoryResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        categoryBottomSheet = CategoryBottomSheet.getInstance(it.data)
                        categoryBottomSheet.show(supportFragmentManager, "categoryBottomSheet")
                    }
                }

                is NetworkResult.Error -> {
                    hideProgressbar()
                }

                is NetworkResult.Loading -> {
                    showProgressbar()
                }
            }
        }
        viewModel.allImageResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        if (binding.recyclerview.adapter == null) {
                            setRVAdapter(it.data)
                        } else if (binding.recyclerview.adapter != null && currentPage == 0) {
                            binding.recyclerview.adapter = null
                            setRVAdapter(it.data)
                        } else {
                            adapter.removeLoadingFooter()
                            isLoadingPage = false
                            adapter.addAll(it.data)
                        }
                        if (it.data.size < 10) {
                            isLastPage1 = true
                        } else {
                            if (selectedCategory == 0) {
                                adapter.addLoadingFooter()
                            }
                        }
                        isLoadingPage = false
                    }
                }

                is NetworkResult.Error -> {
                    hideProgressbar()
                    isLoadingPage = false
                }

                is NetworkResult.Loading -> {
                    Log.i("NetworkResult.Loading ", "" + currentPage)
                    if (currentPage == 0) {
                        showProgressbar()
                    }
                }
            }
        }
        viewModel.catImageResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        replaceItem(it.data)
                        binding.recyclerview.adapter = null
                        if (binding.recyclerview.adapter == null) {
                            setRVAdapter(it.data)
                        }
                    }
                }

                is NetworkResult.Error -> {
                    hideProgressbar()
                }

                is NetworkResult.Loading -> {
                    showProgressbar()
                }
            }
        }
    }

    private fun fetchCategories() {
        viewModel.fetchCategories("2")
    }

    private fun fetchImages(page: Int = 0) {
        viewModel.fetchImages(page, 10)
    }

    fun fetchImagesById(id: Int, parentCateId: Int) {

        when (parentCateId) {
            0 -> {
                fetchImages(currentPage)
            }
            6 -> {
                isLastPage1 = false
                currentPage = 0
                if (id == 6) {
                    viewModel.fetchImagesByCatId(id.toString(), "2")
                } else {
                    viewModel.fetchImagesBySubCatId(id.toString(), "2")
                }
            }
            else -> {
                isLastPage1 = false
                currentPage = 0
                viewModel.fetchImagesByCatId(id.toString(), "2")
            }
        }
        dismissCategoryBottomSheet()
    }

    fun dismissCategoryBottomSheet() {
        categoryBottomSheet.dismiss()
    }

    private fun setRVAdapter(arrayList: ArrayList<ImageBean>) {
        val bitmapList = ArrayList<Bitmap>()
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
                startActivity(Intent(this@HomeActivity, CreateStatusActivity::class.java))
            }

            R.id.share_app_iv -> {
                shareApp()
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onActivityResult(requestCode, resultCode, data)")
    )
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /* if (requestCode == 101 && resultCode == RESULT_OK) {
             setProfileData()
             adapter.notifyDataSetChanged()
         }*/
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setProfileData()
        adapter.notifyDataSetChanged()
    }
}