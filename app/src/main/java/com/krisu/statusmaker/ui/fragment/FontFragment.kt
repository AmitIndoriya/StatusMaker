package com.krisu.statusmaker.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.FragAlignmentLayoutBinding
import com.krisu.statusmaker.databinding.FragFontLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.ColorAdapter

class FontFragment : Fragment() {
    lateinit var binding: FragFontLayoutBinding
    lateinit var activity: CreateStatusActivity

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSeekbarLimit() {/* seekBar.min = (activity).textSize.toInt()
         seekBar.max = (activity).textSize.toInt() + 20*/
        if(this::binding.isInitialized){
            binding.seekbar.progress = (activity as CreateStatusActivity).textSize.toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragFontLayoutBinding.inflate(inflater, container, false)

        setAdapter()
        setSeekbar()
        return binding.root
    }

    private fun setAdapter() {
        binding.recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView.layoutManager =
            GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = ColorAdapter(activity, parentFragment)
    }
    // Here "layout_login" is a name of layout file
    // created for LoginFragment


    private fun setSeekbar() {

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar, progress: Int, fromUser: Boolean
            ) {
                (parentFragment as ShareStatusFragment).changeFontSize(progress.toFloat())
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(
                    requireActivity(), "Progress is: " + seek.progress + "%", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}