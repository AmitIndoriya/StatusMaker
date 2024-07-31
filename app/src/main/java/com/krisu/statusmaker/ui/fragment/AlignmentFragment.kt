package com.krisu.statusmaker.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.FragAlignmentLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity

class AlignmentFragment : Fragment(), OnClickListener {
    lateinit var binding: FragAlignmentLayoutBinding
    lateinit var activity: CreateStatusActivity
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragAlignmentLayoutBinding.inflate(inflater, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.leftAlign.setOnClickListener(this)
        binding.centerAlign.setOnClickListener(this)
        binding.rightAlign.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.left_align -> {
                (parentFragment as ShareStatusFragment).changeAlignment(2)
            }

            R.id.center_align -> {
                (parentFragment as ShareStatusFragment).changeAlignment(1)
            }

            R.id.right_align -> {
                (parentFragment as ShareStatusFragment).changeAlignment(3)
            }
        }
    }
}