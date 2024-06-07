package com.krisu.statusmaker.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.krisu.statusmaker.databinding.FragColorLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.ColorAdapter


class ColorFragment : Fragment() {
    lateinit var activity: CreateStatusActivity
    lateinit var binding: FragColorLayoutBinding
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragColorLayoutBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    fun setAdapter() {
        binding.recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = ColorAdapter(activity)
    }
}