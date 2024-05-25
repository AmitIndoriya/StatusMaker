package com.krisu.statusmaker.ui.activity

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActEditDesignBinding
import com.krisu.statusmaker.ui.adapter.DashboardVPFragmentAdapter
import com.krisu.statusmaker.ui.fragment.EditDesignFragment
import com.krisu.statusmaker.utils.IntentConstants
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.krisu.statusmaker.viewmodel.EditDesignViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer


@AndroidEntryPoint
class EditDesignActivity : BaseActivity(), OnClickListener {
    lateinit var binding: ActEditDesignBinding
    val viewModel by viewModels<EditDesignViewModel>()
    private lateinit var timer: Timer
    lateinit var adapter: DashboardVPFragmentAdapter
    val nameLd = MutableLiveData<String>()
    val phoneNumberLD = MutableLiveData<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        binding = ActEditDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopAndBottomMarginOfScreen()
        getIntentData()
        textChangeListeners()
        setListeners()
    }

    private fun getBitmapFromUrl(url: String) {
        viewModel.getBitmapFromUrl(url)
    }

    private fun setTopAndBottomMarginOfScreen() {
        val layoutParams = binding.toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin = getStatusBarHeight()
        binding.toolbar.layoutParams = layoutParams
        val layoutParams1 = binding.root.layoutParams as FrameLayout.LayoutParams
        layoutParams1.bottomMargin = getNavigationBarHeight()
        binding.root.layoutParams = layoutParams1
    }

    private fun getIntentData() {
        val type = intent.getIntExtra(IntentConstants.SELECTED_PAGE, 0)
        val imgUrl = intent.getStringExtra(IntentConstants.IMG_URL)
        getBitmapFromUrl(imgUrl!!)
        setUpViewpager(type, imgUrl)
        setProfileInfo()
    }

    private fun setProfileInfo() {
        if (!TextUtils.isEmpty(Utils.getStringInSP(this, PreferenceConstant.PROFILE_NAME))) {
            binding.nameEt.setText(Utils.getStringInSP(this, PreferenceConstant.PROFILE_NAME))
        }
        if (!TextUtils.isEmpty(Utils.getStringInSP(this, PreferenceConstant.MOBILE_NUMBER))) {
            binding.phoneNumberEt.setText(
                Utils.getStringInSP(
                    this, PreferenceConstant.MOBILE_NUMBER
                )
            )
        }
    }

    private fun setUpViewpager(type: Int, imgUrl: String) {
        val fragList = ArrayList<Fragment>()
        fragList.add(EditDesignFragment.getInstance(0, imgUrl))
        fragList.add(EditDesignFragment.getInstance(1, imgUrl))
        fragList.add(EditDesignFragment.getInstance(2, imgUrl))
        fragList.add(EditDesignFragment.getInstance(3, imgUrl))
        adapter = DashboardVPFragmentAdapter(supportFragmentManager, fragList)
        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.attachTo(binding.viewPager)
        binding.viewPager.offscreenPageLimit = 4
        binding.viewPager.currentItem = type
    }

    private fun setListeners() {
        binding.shareTv.setOnClickListener(this)
    }

    private fun textChangeListeners() {
        binding.nameEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nameLd.value = s.toString()
            }
        })
        binding.phoneNumberEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                phoneNumberLD.value = s.toString()
            }
        })
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.share_tv -> {
                val fragment = adapter.getItem(binding.viewPager.currentItem) as EditDesignFragment
                viewToImage(fragment.getViewToShare(), "")
            }
        }
    }
}