package com.krisu.statusmaker.ui.activity

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.krisu.statusmaker.databinding.ActAvatarLayoutBinding
import com.krisu.statusmaker.model.AvatarModel
import com.krisu.statusmaker.ui.adapter.AvatarAdapter
import com.krisu.statusmaker.viewmodel.AvatarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvatarActivity : BaseActivity() {
    lateinit var binding: ActAvatarLayoutBinding
    private val viewModel by viewModels<AvatarViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        binding = ActAvatarLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopAndBottomMarginOfScreen()
        getAvatarList()
    }

    private fun getAvatarList() {
        viewModel.getAvatarList()
        viewModel.avatarListLD.observe(this) {
            setData(it)
        }
    }

    private fun setData(hashMap: HashMap<Int, AvatarModel>) {
        binding.recyclerview.layoutManager = GridLayoutManager(this, 4)
        val adapter = AvatarAdapter(this, hashMap)
        binding.recyclerview.adapter = adapter
    }

    private fun setTopAndBottomMarginOfScreen() {
        val layoutParams = binding.toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin = getStatusBarHeight()
        binding.toolbar.layoutParams = layoutParams
        val layoutParams1 = binding.root.layoutParams as FrameLayout.LayoutParams
        layoutParams1.bottomMargin = getNavigationBarHeight()
        binding.root.layoutParams = layoutParams1
    }
}