package com.krisu.statusmaker.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.krisu.statusmaker.databinding.DialogAddMulticolorBinding
import com.krisu.statusmaker.model.MultiColorBean
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.ColorAdapter
import com.krisu.statusmaker.ui.fragment.MultiColorFragment


class AddMultiColorDialog : DialogFragment() {
    lateinit var binding: DialogAddMulticolorBinding
    var multiColorBean: MultiColorBean? = null

    companion object {
        fun newInstance(multiColorBean: MultiColorBean?) = AddMultiColorDialog().apply {
            arguments = Bundle().apply {
                putSerializable("data", multiColorBean)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle?.getSerializable("data") != null) {
            multiColorBean = bundle.getSerializable("data") as MultiColorBean
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    @SuppressLint("SetTextI18n")
    fun setData() {
        if (multiColorBean != null) {
            binding.startRangeEt.setText(multiColorBean!!.start.toString())
            binding.endRangeEt.setText(multiColorBean!!.end.toString())
            setTextColorAdapter(multiColorBean!!.textColor)
            setShadowAdapter(multiColorBean!!.shadowColor)
        } else {
            setTextColorAdapter()
            setShadowAdapter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddMulticolorBinding.inflate(inflater, container, false)
        setListeners()
        setData()
        return binding.root
    }

    private fun setListeners() {
        binding.addTv.setOnClickListener {
            if (TextUtils.isEmpty(binding.startRangeEt.text.toString()) || TextUtils.isEmpty(binding.endRangeEt.text.toString())) {
                Toast.makeText(activity, "Please enter range", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.startRangeEt.text.toString()
                    .toInt() > binding.endRangeEt.text.toString().toInt()
            ) {
                Toast.makeText(activity, "Please enter valid range", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (multiColorBean == null) {
                multiColorBean = MultiColorBean(
                    id = (activity as CreateStatusActivity).multiColorMap.size,
                    start = binding.startRangeEt.text.toString().toInt(),
                    end = binding.endRangeEt.text.toString().toInt(),
                    textColor = (parentFragment as MultiColorFragment).textColor,
                    shadowColor = (parentFragment as MultiColorFragment).shadowColor
                )
            } else {
                multiColorBean!!.start = binding.startRangeEt.text.toString().toInt()
                multiColorBean!!.end = binding.endRangeEt.text.toString().toInt()
                multiColorBean!!.textColor = (parentFragment as MultiColorFragment).textColor
                multiColorBean!!.shadowColor = (parentFragment as MultiColorFragment).shadowColor
            }
            (activity as CreateStatusActivity).multiColorMap[multiColorBean!!.id] = multiColorBean!!
            (activity as CreateStatusActivity).multiColorBeanLD.value =
                (activity as CreateStatusActivity).multiColorMap
            hideKeyboard(requireActivity())
            dismiss()

        }

    }

    private fun setTextColorAdapter(selectedPos: Int = 0) {
        binding.recyclerView1.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView1.layoutManager =
            GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerView1.adapter =
            ColorAdapter(
                (requireActivity() as CreateStatusActivity),
                parentFragment,
                parentFragment,
                1,
                selectedPos
            )
    }

    private fun setShadowAdapter(selectedPos: Int = 0) {
        binding.recyclerView2.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView2.layoutManager =
            GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.adapter =
            ColorAdapter(
                (requireActivity() as CreateStatusActivity),
                parentFragment,
                parentFragment,
                2,
                selectedPos
            )
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

}