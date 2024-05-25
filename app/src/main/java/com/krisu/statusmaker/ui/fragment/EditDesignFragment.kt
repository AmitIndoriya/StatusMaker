package com.krisu.statusmaker.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.palette.graphics.Palette
import androidx.viewbinding.ViewBinding
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.BottomProfileView1Binding
import com.krisu.statusmaker.databinding.BottomProfileView2Binding
import com.krisu.statusmaker.databinding.BottomProfileView3Binding
import com.krisu.statusmaker.databinding.BottomProfileView4Binding
import com.krisu.statusmaker.databinding.FragEditDesignLayoutBinding
import com.krisu.statusmaker.ui.activity.EditDesignActivity
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.roundToInt


class EditDesignFragment : BaseFragment() {
    lateinit var binding: FragEditDesignLayoutBinding
    private var type: Int = 0
    private lateinit var imgUrl: String
    private lateinit var inflatedView: ViewBinding


    companion object {
        fun getInstance(type: Int, imgUrl: String) = EditDesignFragment().apply {
            arguments = Bundle(2).apply {
                putSerializable("type", type)
                putSerializable("imgUrl", imgUrl)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            type = bundle.getInt("type")
            imgUrl = bundle.getString("imgUrl").toString()
        }
    }

    private fun addObservers() {
        (requireActivity() as EditDesignActivity).viewModel.avatarListLD.observe(requireActivity()) {
            val avatarId = Utils.getIntInSP(requireActivity(), PreferenceConstant.AVATAR_ID)
            if (avatarId != -1 && this::inflatedView.isInitialized)  {
                (inflatedView.root.findViewById(R.id.profile_img) as ImageView).setImageDrawable(it[avatarId]?.drawable)
            }
        }
        (requireActivity() as EditDesignActivity).nameLd.observe(requireActivity()) {
            setProfileName(it)
        }
        (requireActivity() as EditDesignActivity).phoneNumberLD.observe(requireActivity()) {
            setPhoneNumber(it)
        }
        (requireActivity() as EditDesignActivity).viewModel.bitmapLD.observe(requireActivity()) {
            when (type) {
                0 -> {
                    setData1(it)
                }

                1 -> {
                    setData2(it)
                }

                2 -> {
                    setData3(it)
                }

                3 -> {
                    setData4(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragEditDesignLayoutBinding.inflate(inflater)
        addObservers()
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData1(bitmap: Bitmap) {
        val item: LinearLayout = binding.profileContainer
        inflatedView = BottomProfileView1Binding.inflate(LayoutInflater.from(context))
        item.addView(inflatedView.root)
        binding.imageView.setImageBitmap(bitmap)
        generatePalette(bitmap, (inflatedView as BottomProfileView1Binding).profileBg1, 0)
        generatePalette(bitmap, (inflatedView as BottomProfileView1Binding).profileBg2, 2)
        generatePalette(bitmap, (inflatedView as BottomProfileView1Binding).profileInfoLl, 0)
        setProfileData()
    }


    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    private fun setData2(bitmap: Bitmap) {
        val item: LinearLayout = binding.profileContainer
        inflatedView = BottomProfileView2Binding.inflate(LayoutInflater.from(context))
        item.addView(inflatedView.root)
        binding.imageView.setImageBitmap(bitmap)
        generatePalette(bitmap, (inflatedView as BottomProfileView2Binding).profileImg, 0)
        generatePalette(bitmap, (inflatedView as BottomProfileView2Binding).profileInfoLl, 2)
        setProfileData()
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData3(bitmap: Bitmap) {
        val item: LinearLayout = binding.profileContainer
        inflatedView = BottomProfileView3Binding.inflate(LayoutInflater.from(context))
        item.addView(inflatedView.root)
        binding.imageView.setImageBitmap(bitmap)
        generatePalette(bitmap, (inflatedView as BottomProfileView3Binding).profileBg, 2)
        generatePalette(bitmap, (inflatedView as BottomProfileView3Binding).profileInfoLl, 2)
        setProfileData()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData4(bitmap: Bitmap) {
        val item: LinearLayout = binding.profileContainer
        inflatedView = BottomProfileView4Binding.inflate(LayoutInflater.from(context))
        item.addView(inflatedView.root)
        binding.imageView.setImageBitmap(bitmap)
        generatePalette(bitmap, (inflatedView as BottomProfileView4Binding).profileBg1, 0)
        generatePalette(bitmap, (inflatedView as BottomProfileView4Binding).profileBg2, 2)
        generatePalette(bitmap, (inflatedView as BottomProfileView4Binding).profileInfoLl, 2)
        setProfileData()
    }

    private fun setProfileData() {
        if (Utils.getBooleanInSP(requireActivity(), PreferenceConstant.IS_AVATAR_SELECTED)) {
            (requireActivity() as EditDesignActivity).viewModel.getAvatarList()
        } else {
            if (!TextUtils.isEmpty(
                    Utils.getStringInSP(
                        requireActivity(), PreferenceConstant.PROFILE_IMG
                    )
                )
            ) {
                setProfileImage(
                    Utils.getStringInSP(
                        requireActivity(), PreferenceConstant.PROFILE_IMG
                    )
                )
            }
        }

        setProfileName(Utils.getStringInSP(requireActivity(), "profile_name"))
        setPhoneNumber(Utils.getStringInSP(requireActivity(), "mobile_number"))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setProfileImage(url: String?) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context).load(url)
                .into(inflatedView.root.findViewById(R.id.profile_img) as ImageView)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setProfileName(name: String?) {
        if (!TextUtils.isEmpty(name)) {
            (inflatedView.root.findViewById(R.id.profile_name) as TextView).text = name
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPhoneNumber(name: String?) {
        if (!TextUtils.isEmpty(name)) {
            (inflatedView.root.findViewById(R.id.number_tv) as TextView).text = name
        }
    }

    private fun generatePalette(bitmap: Bitmap, view: View, paletteType: Int) {
        val palette = Palette.from(bitmap).generate()
        val paletteArrList = ArrayList<Palette.Swatch?>()
        paletteArrList.add(palette.vibrantSwatch)
        paletteArrList.add(palette.lightVibrantSwatch)
        paletteArrList.add(palette.darkVibrantSwatch)
        paletteArrList.add(palette.mutedSwatch)
        paletteArrList.add(palette.lightMutedSwatch)
        paletteArrList.add(palette.darkMutedSwatch)
        paletteArrList.add(palette.dominantSwatch)

        when (view) {
            is CircleImageView -> {
                setBorderColor(paletteArrList[paletteType], view)
            }

            is ImageView -> {
                setupColorProfileBg(
                    paletteArrList[paletteType] /*palette.vibrantSwatch*/,
                    view,
                    R.drawable.rect_rounded_bg2
                )
            }

            is LinearLayout -> {
                setupTransparentColor(
                    paletteArrList[paletteType]/*palette.darkVibrantSwatch*/,
                    view,
                    R.drawable.rect_rounded_bg1
                )
            }
        }
    }


    private fun setupColorProfileBg(
        swatch: Palette.Swatch?, imageView: ImageView, drawable: Int
    ) {
        val color = swatch?.rgb ?: ContextCompat.getColor(requireActivity(), R.color.white)
        val unwrappedDrawable = AppCompatResources.getDrawable(requireActivity(), drawable)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, color)
        imageView.setBackgroundDrawable(wrappedDrawable)
        //textView.setTextColor(swatch?.titleTextColor ?: ContextCompat.getColor(this, R.color.black))
    }

    private fun setBorderColor(swatch: Palette.Swatch?, imageView: CircleImageView) {
        val color = swatch?.rgb ?: ContextCompat.getColor(requireActivity(), R.color.white)
        imageView.borderColor = color
    }

    private fun setupTransparentColor(
        swatch: Palette.Swatch?, linearLayout: LinearLayout, drawable: Int
    ) {
        val color = swatch?.rgb ?: ContextCompat.getColor(requireActivity(), R.color.white)
        val generatedColor = generateTransparentColor(color, 0.4)
        val unwrappedDrawable = AppCompatResources.getDrawable(requireActivity(), drawable)
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

    fun getViewToShare(): View {
        return binding.cardView
    }
}