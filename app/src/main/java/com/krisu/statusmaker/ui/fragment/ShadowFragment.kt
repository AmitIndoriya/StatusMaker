package com.krisu.statusmaker.ui.fragment

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.FragShadowLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.ColorAdapter

class ShadowFragment : Fragment() {
    lateinit var binding: FragShadowLayoutBinding
    lateinit var activity: CreateStatusActivity
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSeekbarLimit() {/*seekBar.min = (activity).textStrokeWidth.toInt()
        seekBar.max = (activity).textStrokeWidth.toInt() + 10*/
        binding.seekbar.progress = 5
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragShadowLayoutBinding.inflate(inflater, container, false)
        setAdapter()
        setSeekbarLimit()
        setSeekbar()
        return binding.root
    }

    private fun setAdapter() {
        binding.recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = ColorAdapter(activity, parentFragment)
    }

    private fun setSeekbar() {
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar, progress: Int, fromUser: Boolean
            ) {
                (parentFragment as ShareStatusFragment).changeStrokeWidht(progress.toFloat() / 10.toFloat())
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }


            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })
    }
}