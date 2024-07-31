package com.krisu.statusmaker.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.FragChangeBackgroundLayoutBinding
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.ColorAdapter
import com.krisu.statusmaker.ui.adapter.ImageAdapter

class ChangeBackgroundFragment : BaseFragment() {
    lateinit var activity: CreateStatusActivity
    lateinit var binding: FragChangeBackgroundLayoutBinding
    lateinit var callback: CallBack

    companion object {
        fun getInstance() = ChangeBackgroundFragment()
    }

    fun setCallbackListener(callback: CallBack) {
        this.callback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.viewModel.fetchImages()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragChangeBackgroundLayoutBinding.inflate(inflater, container, false)
        activity.viewModel.imageListLD.observe(activity) {
            setAdapter(it)
        }
        activity.changeToolbar(resources.getString(R.string.share_status), 0)
        return binding.root
    }

    private fun setAdapter(arrayList: ArrayList<ImageBean>) {
        binding.recyclerview.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerview.layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerview.adapter =
            ImageAdapter(activity, this@ChangeBackgroundFragment, arrayList)
    }

    fun changeBackground(url: String) {
        callback.changeBackground(url)
    }

    interface CallBack {
        fun changeBackground(url: String)
    }
}