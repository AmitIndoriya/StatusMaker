package com.krisu.statusmaker.ui.adapter

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
import com.krisu.statusmaker.databinding.FragSpacingLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.fragment.ShareStatusFragment

class SpaceingFragment : Fragment() {
    lateinit var binding: FragSpacingLayoutBinding
    lateinit var activity: CreateStatusActivity

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSeekbarLimit() {
        if (this::binding.isInitialized) {
            binding.seekbar.progress = activity.textSize.toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragSpacingLayoutBinding.inflate(inflater, container, false)
        setSeekbar()
        return binding.root
    }


    private fun setSeekbar() {

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar, progress: Int, fromUser: Boolean
            ) {
                (parentFragment as ShareStatusFragment).changeLetterSpacing(progress.toFloat() / 100)
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
        binding.seekbar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar, progress: Int, fromUser: Boolean
            ) {
                (parentFragment as ShareStatusFragment).changeLineSpacing(progress.toFloat() / 10)
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