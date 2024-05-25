package com.krisu.statusmaker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.BottomSheetImgChooserLayoutBinding
import com.krisu.statusmaker.ui.activity.ProfileActivity

class ProfileImgChooserBottomSheet : BottomSheetDialogFragment(), OnClickListener {
    private lateinit var binding: BottomSheetImgChooserLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetImgChooserLayoutBinding.inflate(inflater, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.avatarTv.setOnClickListener(this)
        binding.cameraTv.setOnClickListener(this)
        binding.galleryTv.setOnClickListener(this)
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

    companion object {
        const val TAG = "ModalBottomSheetDialog"
    }

    override fun onClick(v: View) {
        when (v.id) {
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
        }
    }
}