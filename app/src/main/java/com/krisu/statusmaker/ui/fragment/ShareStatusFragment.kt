package com.krisu.statusmaker.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.FragShareStatusLayoutBinding
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.ViewPagerAdapter
import com.krisu.statusmaker.ui.callback.TextEditListener
import com.krisu.statusmaker.utils.Utils
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class ShareStatusFragment : BaseFragment(), TextEditListener {
    lateinit var activity: CreateStatusActivity
    lateinit var binding: FragShareStatusLayoutBinding
    lateinit var callback: ChangeBackgroundFragment.CallBack
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    lateinit var adapter: ViewPagerAdapter
    lateinit var currentFragment: Fragment

    companion object {
        fun getInstance() = ShareStatusFragment()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as CreateStatusActivity
    }

    private fun addObservers() {
        activity.bgUrlLD.observe(activity) {
            changeBackground(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragShareStatusLayoutBinding.inflate(inflater, container, false)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layout.persistentBottomSheet)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.maxHeight = 710
        setupViewPager(binding.layout.tabViewpager)
        setListeners()
        setTouchListener()
        setData()
        addObservers()
        activity.changeToolbar(resources.getString(R.string.create_status), 2)
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
            activity.textSize = binding.textview.textSize
            setTextSize()
            (adapter.getItem(0) as FontFragment).setSeekbarLimit()
        }, 10)

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



    fun changeFontSize(scaleFact: Float) {
        if (activity.textSize < scaleFact) {
            binding.textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleFact)
            binding.textview1.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleFact)
            activity.scaleFact = scaleFact
        }
    }

    private fun setTextSize() {
        if (activity.scaleFact != 0f) {
            binding.textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.scaleFact)
            binding.textview1.setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.scaleFact)
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
        binding.textview.text = activity.suvicharStr
        binding.textview.paint.strokeWidth = activity.textStrokeWidth + width
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

    fun changeBackground(url: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                binding.imageView.setImageBitmap(bitmap)
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                binding.imageView.setImageResource(R.drawable.default_suvichar_bg)
            }
        }
        Picasso.with(context)
            .load(url)
            .into(target)
    }

    private fun setData() {
        binding.textview.text = activity.suvicharStr
        binding.textview1.text = activity.suvicharStr
        setProfileData()
    }

    private fun setProfileData() {
        val bitmap = activity.getImageFromCache()
        binding.imageView.setImageBitmap(bitmap)
        val palette = Palette.from(bitmap).generate()
        val paletteArrList = ArrayList<Palette.Swatch?>()
        paletteArrList.add(palette.vibrantSwatch)
        paletteArrList.add(palette.lightVibrantSwatch)
        paletteArrList.add(palette.darkVibrantSwatch)
        paletteArrList.add(palette.mutedSwatch)
        paletteArrList.add(palette.lightMutedSwatch)
        paletteArrList.add(palette.darkMutedSwatch)
        paletteArrList.add(palette.dominantSwatch)
        setupColorProfileBg(
            palette.vibrantSwatch,
            binding.profileBg1,
            R.drawable.rect_rounded_bg2
        )
        setupColorProfileBg(
            palette.darkVibrantSwatch,
            binding.profileBg2,
            R.drawable.rect_rounded_bg2
        )
        setupTransparentColor(
            palette.vibrantSwatch,
            binding.linearLayout,
            R.drawable.rect_rounded_bg1
        )
        Picasso.with(activity).load(Utils.getStringInSP(activity, "img_url"))
            .into(binding.profileImg)
    }

    private fun setupColorProfileBg(
        swatch: Palette.Swatch?, imageView: ImageView, drawable: Int
    ) {
        val color = swatch?.rgb ?: ContextCompat.getColor(activity, R.color.white)
        val unwrappedDrawable = AppCompatResources.getDrawable(activity, drawable)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, color)
        imageView.setBackgroundDrawable(wrappedDrawable)
    }

    private fun setupTransparentColor(
        swatch: Palette.Swatch?, linearLayout: LinearLayout, drawable: Int
    ) {
        val color = swatch?.rgb ?: ContextCompat.getColor(activity, R.color.white)
        val generatedColor = generateTransparentColor(color, 0.4)
        val unwrappedDrawable = AppCompatResources.getDrawable(activity, drawable)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, /*color*/generatedColor)
        linearLayout.setBackgroundDrawable(wrappedDrawable)
        //textView.setTextColor(swatch?.titleTextColor ?: ContextCompat.getColor(this, R.color.black))
    }

    private fun generateTransparentColor(color: Int, alpha: Double?): Int {
        val defaultAlpha = 255 // (0 - Invisible / 255 - Max visibility)
        val colorAlpha = alpha?.times(defaultAlpha)?.roundToInt() ?: defaultAlpha
        return ColorUtils.setAlphaComponent(color, colorAlpha)
    }

    fun shareStatus() {
        activity.shareImage(true, "")
    }
}