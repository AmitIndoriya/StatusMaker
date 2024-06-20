package com.krisu.statusmaker.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisu.statusmaker.databinding.FragMultiColorLayoutBinding
import com.krisu.statusmaker.model.MultiColorBean
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.MultiColorAdapter
import com.krisu.statusmaker.ui.dialog.AddMultiColorDialog

class MultiColorFragment : Fragment() {
    lateinit var binding: FragMultiColorLayoutBinding
    lateinit var activity: CreateStatusActivity
    var textColor = 0
    var shadowColor = 1

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragMultiColorLayoutBinding.inflate(inflater, container, false)
        setListeners()
        addObserver()
        return binding.root
    }

    private fun setListeners() {
        binding.addTv.setOnClickListener {
            openDialog()
        }
    }

    private fun addObserver() {
        activity.multiColorBeanLD.observe(activity) {
            setAdapter(it)
        }
    }

    private fun setAdapter(hashMap: HashMap<Int, MultiColorBean>) {
        binding.recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter =
            MultiColorAdapter(activity, this@MultiColorFragment, hashMap)
    }

    fun openDialog(multiColorBean: MultiColorBean? = null) {
        val dialog = AddMultiColorDialog.newInstance(multiColorBean)
        dialog.show(childFragmentManager, "AddMultiColorDialog")
    }
}