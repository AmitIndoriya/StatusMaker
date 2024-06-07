package com.krisu.statusmaker.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.krisu.statusmaker.databinding.FragChangeBackgroundLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.ColorAdapter

class ChangeBackgroundFragment: BaseFragment() {
    lateinit var activity: CreateStatusActivity
    lateinit var binding: FragChangeBackgroundLayoutBinding

    companion object {
        fun getInstance() = ChangeBackgroundFragment()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragChangeBackgroundLayoutBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        binding.recyclerview.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerview.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerview.adapter = ColorAdapter(activity)
    }
}