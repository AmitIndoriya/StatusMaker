package com.krisu.statusmaker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.krisu.statusmaker.databinding.BottomSheetCategoryLayoutBinding
import com.krisu.statusmaker.model.CategoryBean
import com.krisu.statusmaker.ui.adapter.CategoryAdapter
import com.krisu.statusmaker.ui.fragment.EditDesignFragment


class CategoryBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var binding: BottomSheetCategoryLayoutBinding
    lateinit var arrayList: ArrayList<CategoryBean>

    companion object {
        fun getInstance(arrayList: ArrayList<CategoryBean>) = CategoryBottomSheet().apply {
            arguments = Bundle(2).apply {
                putSerializable("arrayList", arrayList)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            arrayList = bundle.getSerializable("arrayList") as ArrayList<CategoryBean>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetCategoryLayoutBinding.inflate(inflater, container, false)
        setListeners()
        setRVData()
        return binding.root
    }

    private fun setListeners() {
        /*   binding.avatarTv.setOnClickListener(this)
           binding.cameraTv.setOnClickListener(this)
           binding.galleryTv.setOnClickListener(this)*/
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // used to show the bottom sheet dialog
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    private fun setRVData() {
        val flexboxLayoutManager = FlexboxLayoutManager(requireActivity())
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        val adapter = CategoryAdapter(requireActivity(), arrayList)
        binding.recyclerview.layoutManager = flexboxLayoutManager
        binding.recyclerview.adapter = adapter
    }


    override fun onClick(v: View) {
        /*  when (v.id) {
              R.id.avatar_tv -> {
                  (requireActivity() as ProfileActivity).openAvatar()
                  dismiss()
              }

              R.id.camera_tv -> {
                  (requireActivity() as ProfileActivity).openCamera()
                  dismiss()
              }

              R.id.gallery_tv -> {
                  (requireActivity() as ProfileActivity).openGallery()
                  dismiss()
              }
          }*/
    }
}