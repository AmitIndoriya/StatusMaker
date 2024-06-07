package com.krisu.statusmaker.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.FragColorLayoutBinding
import com.krisu.statusmaker.databinding.FragCreateStatusLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.activity.ShareStatusActivity
import com.krisu.statusmaker.ui.adapter.ViewPagerAdapter
import com.krisu.statusmaker.ui.callback.TextEditListener

class CreateStatusFragment : BaseFragment(), TextEditListener {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var activity: CreateStatusActivity
    lateinit var binding: FragCreateStatusLayoutBinding
    var suvicharStr = ""
    var textSize = 0f
    var textStrokeWidth = 5f
    lateinit var adapter: ViewPagerAdapter
    lateinit var currentFragment: Fragment

    companion object {
        fun getInstance() = CreateStatusFragment()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragCreateStatusLayoutBinding.inflate(inflater, container, false)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layout.persistentBottomSheet)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.maxHeight = 710
        setupViewPager(binding.layout.tabViewpager)
        setupEdittext()
        setListeners()
        setTouchListener()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupViewPager(viewpager: ViewPager) {
        adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FontFragment(), "font")
        adapter.addFragment(ColorFragment(), "color")
        adapter.addFragment(AlignmentFragment(), "Alignment")
        adapter.addFragment(ShadowFragment(), "Shadow")
        viewpager.adapter = adapter
        binding.layout.tabTablayout.setupWithViewPager(binding.layout.tabViewpager)
        val tabText = arrayListOf("Fonts", "Color", "Align", "Shadow")
        for (i in 0..3) {
            getTabView(i, tabText[i]).also {
                binding.layout.tabTablayout.getTabAt(i)?.customView = it
            }
        }
        binding.layout.tabTablayout.selectTab(binding.layout.tabTablayout.getTabAt(0))
        binding.layout.tabTablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.view.findViewById<TextView>(R.id.tabtext)
                    .setTextColor(resources.getColor(R.color.gold, null))
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.findViewById<TextView>(R.id.tabtext)
                    .setTextColor(resources.getColor(R.color.black, null))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        Handler().postDelayed({
            textSize = binding.editText.textSize
            (adapter.getItem(0) as FontFragment).setSeekbarLimit()
        }, 100)

    }

    private fun getTabView(pos: Int, text: String?): View? {
        val view = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_tab_layout, null)
        val tv = view.findViewById<TextView>(R.id.tabtext)
        tv.text = text
        if (pos == 0) {
            tv?.setTextColor(
                ContextCompat.getColor(
                    requireActivity(), R.color.gold
                )
            )
        } else {
            tv?.setTextColor(
                ContextCompat.getColor(
                    requireActivity(), R.color.black
                )
            )
        }
        return view
    }

    private fun setupEdittext() {

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Handler().postDelayed({
                    suvicharStr = s.toString()
                }, 10)

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListeners() {
      /*  binding.nextTv.setOnClickListener {
            binding.editText.visibility = View.GONE
            binding.textview.visibility = View.VISIBLE
            binding.textview1.visibility = View.VISIBLE
            binding.textview.text = suvicharStr
            binding.textview1.text = suvicharStr
            binding.textview.paint.strokeWidth = 5f
            binding.textview.paint.style = Paint.Style.STROKE;
            binding.nextTv.visibility = View.GONE
            binding.saveTv.visibility = View.VISIBLE
        }
        binding.saveTv.setOnClickListener {
            sendToShare()
        }*/
    }

    private fun sendToShare() {
        //saveImageInCache(binding.imageContainer)
        val intent = Intent(requireActivity(), ShareStatusActivity::class.java)
        startActivity(intent)
    }

    fun changeFontSize(scaleFact: Float) {
        if (textSize < scaleFact) {
            binding.textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleFact)
            binding.textview1.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleFact)
            binding.editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleFact)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setTouchListener() {
        var xDown = 0f
        var yDown = 0f
        binding.framelayout.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xDown = event.x
                    yDown = event.y
                }

                MotionEvent.ACTION_UP -> {}
                MotionEvent.ACTION_MOVE -> {
                    val xMove: Float = event.x
                    val yMove: Float = event.y
                    val distX: Float = xMove - xDown
                    val distY: Float = yMove - yDown
                    v.x = v.x + distX
                    v.y = v.y + distY
                }
            }
            true
        }
    }

    override fun changeTextColor(color: Int) {
        binding.textview1.setTextColor(resources.getColor(color))
    }

    fun changeColor(color: Int) {
        currentFragment = adapter.getItem(binding.layout.tabViewpager.currentItem)
        if (currentFragment is ShadowFragment) {
            changeStrokeColor(color)
        } else if (currentFragment is ColorFragment) {
            changeTextColor(color)
        }
    }

    override fun changeStrokeColor(color: Int) {
        binding.textview.setTextColor(resources.getColor(color))
    }

    override fun changeStrokeWidht(width: Float) {
        binding.textview.text = suvicharStr
        binding.textview.paint.strokeWidth = textStrokeWidth + width
        binding.textview.paint.style = Paint.Style.STROKE;
    }

    override fun changeAlignment(type: Int) {
        when (type) {
            1 -> {
                binding.textview.gravity = Gravity.CENTER_HORIZONTAL
                binding.textview1.gravity = Gravity.CENTER_HORIZONTAL
            }

            2 -> {
                binding.textview.gravity = Gravity.START
                binding.textview1.gravity = Gravity.START
            }

            3 -> {
                binding.textview.gravity = Gravity.END
                binding.textview1.gravity = Gravity.END
            }
        }

    }
}