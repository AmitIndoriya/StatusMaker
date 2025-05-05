package com.krisu.statusmaker.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActHomeLayoutNewBinding
import com.krisu.statusmaker.model.CategoryBean
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.ui.adapter.CategoryAdapter
import com.krisu.statusmaker.ui.adapter.HomeRVAdapterNew
import com.krisu.statusmaker.utils.NetworkResult
import com.krisu.statusmaker.utils.PaginationScrollListener
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.krisu.statusmaker.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class HomeActivityNew : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActHomeLayoutNewBinding
    val viewModel by viewModels<HomeViewModel>()
    lateinit var adapter: HomeRVAdapterNew
    var selectedCategory = 0
    private val PAGE_START = 0
    private var isLoadingPage = false
    private var isLastPage1 = false
    private val TOTAL_PAGES = 10
    private var currentPage = PAGE_START
    val layoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        binding = ActHomeLayoutNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigationListener()
        addObservers()
        setToolbarMargin()
        setBottomMargin()
        fetchCatAndImgTogether()
        setListeners()
        setProfileData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setProfileData()
        adapter.notifyDataSetChanged()
    }

    private fun setToolbarMargin() {
        binding.toolbarLl.setPadding(0, getStatusBarHeight(), 0, 0)
    }

    private fun setBottomMargin() {
        binding.root.setPadding(0, 0, 0, getNavigationBarHeight())
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

    fun setListeners() {
        binding.profileIv.setOnClickListener(this)
        binding.createTv.setOnClickListener(this)
        binding.shareAppIv.setOnClickListener(this)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_1 -> startActivity(
                    Intent(
                        this@HomeActivityNew,
                        CreateStatusActivity::class.java
                    )
                )

                R.id.item_2 -> shareApp()
            }
            true
        }
        binding.recyclerView1.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (!isLastPage1 && !isLoadingPage && selectedCategory == 0) {
                    isLoadingPage = true
                    Handler().postDelayed({
                        currentPage++
                        fetchImages(currentPage)
                        Log.i("currentPage", "--$currentPage")

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

    private fun setBottomNavigationListener() {
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    true
                }

                R.id.item_2 -> {
                    true
                }

                else -> false
            }
        }
    }

    private fun fetchCatAndImgTogether() {
        try {
            viewModel.fetchCatAndImgTogether(
                currentPage,
                10,
                Calendar.getInstance().timeInMillis,
                "2"
            )
        } catch (e: Exception) {
            Log.i("Exception", e.message.toString())
        }
    }

    private fun fetchCategories() {
        try {
            viewModel.fetchCategories("2")
        } catch (e: Exception) {
            Log.i("Exception", e.message.toString())
        }

    }

    private fun fetchImages(page: Int = 0) {
        viewModel.fetchImages(page, 10, Calendar.getInstance().timeInMillis)
    }

    private fun addObservers() {
        viewModel.avatarListLD.observe(this) {
            val avatarId = Utils.getIntInSP(this, PreferenceConstant.AVATAR_ID)
            if (avatarId != -1) {
                binding.profileIv.setImageDrawable(it[avatarId]?.drawable)
            }
        }
        viewModel.categoryResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        setCategoryRVData(it.data)
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
                        try {
                            Log.i("cat_response","cat_response")
                            binding.recyclerView1.adapter = null
                            if (binding.recyclerView1.adapter == null) {
                                setRVAdapter(it.data)
                            }
                        } catch (_: Exception) {

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

    private fun setCategoryRVData(arrayList: ArrayList<CategoryBean>) {
        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        val adapter = CategoryAdapter(this, arrayList)
        binding.recyclerview.layoutManager = flexboxLayoutManager
        binding.recyclerview.adapter = adapter
    }

    private fun fetchData() {
        fetchImages(currentPage)
    }

    private fun setRVAdapter(arrayList: ArrayList<ImageBean>) {
        val bitmapList = ArrayList<Bitmap>()
        binding.recyclerView1.layoutManager = layoutManager
        adapter = HomeRVAdapterNew(this, arrayList, bitmapList)
        binding.recyclerView1.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView1)
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
                startActivity(Intent(this@HomeActivityNew, CreateStatusActivity::class.java))
            }

            R.id.share_app_iv -> {
                shareApp()
            }
        }
    }

    fun fetchImagesById(id: Int, parentCateId: Int) {
        Log.i("parentCateId",""+parentCateId)
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
    }
}