package com.krisu.statusmaker.ui.fragment


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
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
import com.krisu.statusmaker.model.MultiColorBean
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.adapter.SpaceingFragment
import com.krisu.statusmaker.ui.adapter.ViewPagerAdapter
import com.krisu.statusmaker.ui.callback.TextEditListener
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class ShareStatusFragment : BaseFragment(), TextEditListener {
    lateinit var activity: CreateStatusActivity
    lateinit var binding: FragShareStatusLayoutBinding
    lateinit var callback: ChangeBackgroundFragment.CallBack
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    lateinit var adapter: ViewPagerAdapter
    private lateinit var currentFragment: Fragment

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
        activity.viewModel.avatarListLD.observe(activity) {
            val avatarId = Utils.getIntInSP(activity, PreferenceConstant.AVATAR_ID)
            if (avatarId != -1) {
                binding.profileImg.setImageDrawable(it[avatarId]?.drawable)
            }
        }
        activity.multiColorBeanLD.observe(activity) {
            binding.textView.text =
                applyTint(it, activity.suvicharStr, false)
            binding.shadowTextview.text =
                applyTint(it, activity.suvicharStr, true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.i("test", "onCreateView")
        binding = FragShareStatusLayoutBinding.inflate(inflater, container, false)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layout.persistentBottomSheet)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.maxHeight = 710
        setupViewPager(binding.layout.tabViewpager)
        setTouchListener()
        setData()
        activity.changeToolbar(resources.getString(R.string.create_status), 2)
        Handler().postDelayed({
            (adapter.getItem(0) as FontFragment).setSeekbarLimit()
        }, 10)
        addObservers()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupViewPager(viewpager: ViewPager) {
        adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FontFragment(), "fonts")
        adapter.addFragment(ColorFragment(), "color")
        adapter.addFragment(AlignmentFragment(), "Alignment")
        adapter.addFragment(ShadowFragment(), "Shadow")
        adapter.addFragment(MultiColorFragment(), "Multi Color")
        adapter.addFragment(SpaceingFragment(), "Spacing")

        viewpager.adapter = adapter
        binding.layout.tabTablayout.setupWithViewPager(binding.layout.tabViewpager)
        val tabText = arrayListOf("Fonts", "Color", "Align", "Shadow", "Multi Color", "Spacing")
        for (i in 0 until tabText.size) {
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
        if (scaleFact > 60f) {
            binding.shadowTextview.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleFact)
            binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleFact)
            activity.textSize = scaleFact
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
        activity.textColor = color
        binding.textView.setTextColor(resources.getColor(color))
    }

    fun changeColor(color: Int, pos: Int) {
        currentFragment = adapter.getItem(binding.layout.tabViewpager.currentItem)
        if (currentFragment is ShadowFragment) {
            activity.selectedStrokeColorNum = pos
            changeStrokeColor(color)
        } else if (currentFragment is ColorFragment) {
            activity.selectedTextColorNum = pos
            changeTextColor(color)
        }
    }

    override fun changeStrokeColor(color: Int) {
        activity.strokeColor = color
        binding.shadowTextview.setTextColor(resources.getColor(color))
    }

    override fun changeStrokeWidht(width: Float) {
        binding.shadowTextview.text = activity.suvicharStr
        binding.shadowTextview.paint.strokeWidth = activity.textStrokeWidth + width
        binding.shadowTextview.paint.style = Paint.Style.STROKE
    }

    fun changeFont(fontName: String) {
        binding.shadowTextview.typeface = Utils.getCustomFont(activity, fontName)
        binding.textView.typeface = Utils.getCustomFont(activity, fontName)
    }

    override fun changeAlignment(type: Int) {
        activity.textAlignMent = type
        when (type) {
            1 -> {
                binding.shadowTextview.gravity = Gravity.CENTER_HORIZONTAL
                binding.textView.gravity = Gravity.CENTER_HORIZONTAL
            }

            2 -> {
                binding.shadowTextview.gravity = Gravity.START
                binding.textView.gravity = Gravity.START
            }

            3 -> {
                binding.shadowTextview.gravity = Gravity.END
                binding.textView.gravity = Gravity.END
            }
        }

    }

    fun changeLetterSpacing(space: Float) {
        activity.spacing = space
        binding.shadowTextview.letterSpacing = space
        binding.textView.letterSpacing = space
    }

    fun changeLineSpacing(space: Float) {
        binding.shadowTextview.setLineSpacing(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                space,
                resources.displayMetrics
            ), 1.0f
        )
        binding.textView.setLineSpacing(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                space,
                resources.displayMetrics
            ), 1.0f
        )
    }

    private fun changeBackground(url: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                binding.imageView.setImageBitmap(bitmap)
                val palette = bitmap?.let { Palette.from(it).generate() }
                setupColorProfileBg(
                    palette?.vibrantSwatch, binding.profileBg1, R.drawable.rect_rounded_bg2
                )
                setupColorProfileBg(
                    palette?.darkVibrantSwatch, binding.profileBg2, R.drawable.rect_rounded_bg2
                )
                setupTransparentColor(
                    palette?.vibrantSwatch, binding.linearLayout, R.drawable.rect_rounded_bg1
                )
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
                binding.imageView.setImageResource(R.drawable.default_suvichar_bg)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                //binding.imageView.setImageResource(R.drawable.default_suvichar_bg)
            }
        }
        Picasso.with(context).load(url).into(target)
    }


    private fun setData() {
        binding.textView.text = activity.suvicharStr
        binding.textView.setTextColor(resources.getColor(activity.textColor))
        binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.textSize)
        binding.textView.typeface = Utils.getCustomFont(activity, activity.selectedFont)

        binding.shadowTextview.text = activity.suvicharStr
        binding.shadowTextview.setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.textSize)
        binding.shadowTextview.text = activity.suvicharStr
        binding.shadowTextview.paint.strokeWidth = activity.textStrokeWidth
        binding.shadowTextview.paint.style = Paint.Style.STROKE
        binding.shadowTextview.setTextColor(resources.getColor(activity.strokeColor))
        binding.shadowTextview.typeface = Utils.getCustomFont(activity, activity.selectedFont)
        changeAlignment(activity.textAlignMent)
        changeLetterSpacing(activity.spacing)
        setProfileData()
    }

    private fun setProfileData() {
        if (!TextUtils.isEmpty(Utils.getStringInSP(activity, PreferenceConstant.PROFILE_NAME))) {
            binding.profileName.text =
                Utils.getStringInSP(activity, PreferenceConstant.PROFILE_NAME)

        }
        if (!TextUtils.isEmpty(Utils.getStringInSP(activity, PreferenceConstant.MOBILE_NUMBER))) {
            binding.numberTv.text = Utils.getStringInSP(activity, PreferenceConstant.MOBILE_NUMBER)
        }
        if (Utils.getBooleanInSP(activity, PreferenceConstant.IS_AVATAR_SELECTED)) {
            (context as CreateStatusActivity).viewModel.getAvatarList()
        } else {
            if (!TextUtils.isEmpty(Utils.getStringInSP(activity, PreferenceConstant.PROFILE_IMG))) {
                Picasso.with(activity)
                    .load(Utils.getStringInSP(activity, PreferenceConstant.PROFILE_IMG))
                    .into(binding.profileImg)
            }
        }
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
    }

    private fun generateTransparentColor(color: Int, alpha: Double?): Int {
        val defaultAlpha = 255 // (0 - Invisible / 255 - Max visibility)
        val colorAlpha = alpha?.times(defaultAlpha)?.roundToInt() ?: defaultAlpha
        return ColorUtils.setAlphaComponent(color, colorAlpha)
    }

    fun shareStatus() {
        activity.viewToImage(binding.container, "")
    }

    private fun applyTint(
        hashMap: HashMap<Int, MultiColorBean>,
        string: String,
        isShadow: Boolean
    ): SpannableString {
        val colorList = activity.viewModel.getColorList()
        val spannableString = SpannableString(string)
        for (i in 0 until hashMap.size) {
            val color: Int = if (isShadow) {
                activity.resources.getColor(colorList[hashMap[i]!!.shadowColor])
            } else {
                activity.resources.getColor(colorList[hashMap[i]!!.textColor])
            }
            var start = hashMap[i]!!.start
            var end = hashMap[i]!!.end
            if (start < 0) {
                start = 0
            }
            if (end > string.length) {
                end = string.length
            }
            val fcs = ForegroundColorSpan(color)
            spannableString.setSpan(fcs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return spannableString
    }
}