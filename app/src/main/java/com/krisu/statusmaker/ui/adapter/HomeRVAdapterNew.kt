package com.krisu.statusmaker.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.ui.activity.BaseActivity
import com.krisu.statusmaker.ui.activity.EditDesignActivity
import com.krisu.statusmaker.ui.activity.HomeActivity
import com.krisu.statusmaker.utils.IntentConstants
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.roundToInt

class HomeRVAdapterNew(
    private val context: Context,
    private val arrayList: ArrayList<ImageBean>,
    private val bitmapList: ArrayList<Bitmap>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var target: com.squareup.picasso.Target

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> HomeViewHolder1(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_home1, parent, false)
            )

            1 -> HomeViewHolder2(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_home2, parent, false)
            )

            2 -> HomeViewHolder3(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_home3, parent, false)
            )

            3 -> HomeViewHolder4(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_home4, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position % 4) {
            0 -> (holder as HomeViewHolder1).bind(arrayList[position].url)
            1 -> (holder as HomeViewHolder2).bind(arrayList[position].url)
            2 -> (holder as HomeViewHolder3).bind(arrayList[position].url)
            3 -> (holder as HomeViewHolder4).bind(arrayList[position].url)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position % 4
    }

    inner class HomeViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imgUrl: String) {
            val profileImgIV: ImageView = itemView.findViewById(R.id.profile_img)
            val imageView: ImageView = itemView.findViewById(R.id.image_view)
            val profileBg1: ImageView = itemView.findViewById(R.id.profile_bg1)
            val profileBg2: ImageView = itemView.findViewById(R.id.profile_bg2)
            val profileNameTV: TextView = itemView.findViewById(R.id.profile_name)
            val numberTV: TextView = itemView.findViewById(R.id.number_tv)
            val shareTv: TextView = itemView.findViewById(R.id.share_tv)
            val editTv: TextView = itemView.findViewById(R.id.edit_tv)
            val container: RelativeLayout = itemView.findViewById(R.id.container)
            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout)

            setProfileData(profileImgIV, profileNameTV, numberTV)
            setupImage(
                url = imgUrl,
                imageView = imageView,
                imageViewBg1 = profileBg1,
                imageViewBg2 = profileBg2,
                linearLayout = linearLayout,
                pos = layoutPosition
            )
            editTv.setOnClickListener {
                val intent = Intent(context, EditDesignActivity::class.java)
                intent.putExtra(IntentConstants.IMG_URL, arrayList[layoutPosition].url)
                intent.putExtra(IntentConstants.SELECTED_PAGE, 0)
                context.startActivity(intent)
            }
            shareTv.setOnClickListener {
                (context as BaseActivity).viewToImage(container, "")
            }
        }
    }

    inner class HomeViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imgUrl: String) {
            val profileImgIV: ImageView = itemView.findViewById(R.id.profile_img)
            val imageView: ImageView = itemView.findViewById(R.id.image_view)
            val profileNameTV: TextView = itemView.findViewById(R.id.profile_name)
            val numberTV: TextView = itemView.findViewById(R.id.number_tv)
            val shareTv: TextView = itemView.findViewById(R.id.share_tv)
            val editTv: TextView = itemView.findViewById(R.id.edit_tv)
            val container: RelativeLayout = itemView.findViewById(R.id.container)
            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout)

            setProfileData(profileImgIV, profileNameTV, numberTV)
            setupImage(
                url = imgUrl,
                imageView = imageView,
                imageViewBg1 = profileImgIV,
                linearLayout = linearLayout,
                isBorderSet = true,
                pos = layoutPosition
            )
            editTv.setOnClickListener {
                val intent = Intent(context, EditDesignActivity::class.java)
                intent.putExtra(IntentConstants.IMG_URL, arrayList[layoutPosition].url)
                intent.putExtra(IntentConstants.SELECTED_PAGE, 1)
                context.startActivity(intent)
            }
            shareTv.setOnClickListener {
                (context as BaseActivity).viewToImage(container, "")
            }

        }
    }

    inner class HomeViewHolder3(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imgUrl: String) {
            val profileImgIV: ImageView = itemView.findViewById(R.id.profile_img)
            val imageView: ImageView = itemView.findViewById(R.id.image_view)
            val profileBg: ImageView = itemView.findViewById(R.id.profile_bg)
            val profileNameTV: TextView = itemView.findViewById(R.id.profile_name)
            val numberTV: TextView = itemView.findViewById(R.id.number_tv)
            val shareTv: TextView = itemView.findViewById(R.id.share_tv)
            val editTv: TextView = itemView.findViewById(R.id.edit_tv)
            val container: RelativeLayout = itemView.findViewById(R.id.container)
            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout)

            setProfileData(profileImgIV, profileNameTV, numberTV)
            setupImage(
                url = imgUrl,
                imageView = imageView,
                imageViewBg1 = profileBg,
                imageViewBg2 = null,
                linearLayout = linearLayout,
                pos = layoutPosition
            )
            editTv.setOnClickListener {
                val intent = Intent(context, EditDesignActivity::class.java)
                intent.putExtra(IntentConstants.IMG_URL, arrayList[layoutPosition].url)
                intent.putExtra(IntentConstants.SELECTED_PAGE, 2)
                context.startActivity(intent)
            }
            shareTv.setOnClickListener {
                (context as BaseActivity).viewToImage(container, "")
            }
        }
    }

    inner class HomeViewHolder4(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imgUrl: String) {
            val profileImgIV: ImageView = itemView.findViewById(R.id.profile_img)
            val imageView: ImageView = itemView.findViewById(R.id.image_view)
            val profileBg1: ImageView = itemView.findViewById(R.id.profile_bg1)
            val profileBg2: ImageView = itemView.findViewById(R.id.profile_bg2)
            val profileNameTV: TextView = itemView.findViewById(R.id.profile_name)
            val numberTV: TextView = itemView.findViewById(R.id.number_tv)
            val shareTv: TextView = itemView.findViewById(R.id.share_tv)
            val editTv: TextView = itemView.findViewById(R.id.edit_tv)
            val container: RelativeLayout = itemView.findViewById(R.id.container)
            val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout)

            setProfileData(profileImgIV, profileNameTV, numberTV)
            setupImage(
                url = imgUrl,
                imageView = imageView,
                imageViewBg1 = profileBg1,
                imageViewBg2 = profileBg2,
                linearLayout = linearLayout,
                pos = layoutPosition
            )
            editTv.setOnClickListener {
                val intent = Intent(context, EditDesignActivity::class.java)
                intent.putExtra(IntentConstants.IMG_URL, arrayList[layoutPosition].url)
                intent.putExtra(IntentConstants.SELECTED_PAGE, 3)
                context.startActivity(intent)
            }
            shareTv.setOnClickListener {
                (context as BaseActivity).viewToImage(container, "")
            }
        }
    }

    fun setProfileData(imageView: ImageView, nameTv: TextView, mobNumberTv: TextView) {
        if (Utils.getBooleanInSP(context, PreferenceConstant.IS_AVATAR_SELECTED)) {
            val avatarId = Utils.getIntInSP(context, PreferenceConstant.AVATAR_ID)
            if (avatarId != -1) {
                imageView.setImageDrawable(
                    (context as HomeActivity).viewModel.avatarListLD.value?.get(
                        avatarId
                    )?.drawable
                )
            }

        } else {
            if (!TextUtils.isEmpty(Utils.getStringInSP(context, PreferenceConstant.PROFILE_IMG))) {
                Picasso.with(context)
                    .load(Utils.getStringInSP(context, PreferenceConstant.PROFILE_IMG))
                    .into(imageView)
            } else {
                imageView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_profile))
            }
        }
        nameTv.text = Utils.getStringInSP(context, PreferenceConstant.PROFILE_NAME)
        mobNumberTv.text = Utils.getStringInSP(context, PreferenceConstant.MOBILE_NUMBER)
    }

    private fun generatePalete(bitmap: Bitmap, view: View, paletteType: Int) {
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

    private fun setupTransparentColor(
        swatch: Palette.Swatch?, linearLayout: LinearLayout, drawable: Int
    ) {
        val color = swatch?.rgb ?: ContextCompat.getColor(context, R.color.white)
        val generatedColor = generateTransparentColor(color, 0.4)
        val unwrappedDrawable = AppCompatResources.getDrawable(context, drawable)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, /*color*/generatedColor)
        linearLayout.setBackgroundDrawable(wrappedDrawable)
        //textView.setTextColor(swatch?.titleTextColor ?: ContextCompat.getColor(this, R.color.black))
    }

    private fun setupColorProfileBg(
        swatch: Palette.Swatch?, imageView: ImageView, drawable: Int
    ) {
        val color = swatch?.rgb ?: ContextCompat.getColor(context, R.color.white)
        val unwrappedDrawable = AppCompatResources.getDrawable(context, drawable)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, color)
        imageView.setBackgroundDrawable(wrappedDrawable)
        //textView.setTextColor(swatch?.titleTextColor ?: ContextCompat.getColor(this, R.color.black))
    }

    private fun setBorderColor(swatch: Palette.Swatch?, imageView: CircleImageView) {
        val color = swatch?.rgb ?: ContextCompat.getColor(context, R.color.white)
        imageView.borderColor = color
    }

    private fun setupColorProfileText(
        swatch: Palette.Swatch?, view: LinearLayout, drawable: Int
    ) {
        val color = swatch?.rgb ?: ContextCompat.getColor(context, R.color.white)
        val generatedColor = generateTransparentColor(color, 0.4)
        val unwrappedDrawable = AppCompatResources.getDrawable(context, drawable)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, generatedColor)
        view.setBackgroundDrawable(wrappedDrawable)
        //textView.setTextColor(swatch?.titleTextColor ?: ContextCompat.getColor(this, R.color.black))
    }


    private fun generateTransparentColor(color: Int, alpha: Double?): Int {
        val defaultAlpha = 255 // (0 - Invisible / 255 - Max visibility)
        val colorAlpha = alpha?.times(defaultAlpha)?.roundToInt() ?: defaultAlpha
        return ColorUtils.setAlphaComponent(color, colorAlpha)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(arrayList: ArrayList<ImageBean>?, bitmapList: ArrayList<Bitmap>?) {
        if (arrayList != null) {
            this.arrayList.addAll(arrayList)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceItem(arrayList: ArrayList<ImageBean>?) {
        this.arrayList.clear()
        if (arrayList != null) {
            this.arrayList.addAll(arrayList)
        }
        notifyDataSetChanged()
    }

    private fun setupImage(
        url: String,
        imageView: ImageView,
        imageViewBg1: ImageView? = null,
        imageViewBg2: ImageView? = null,
        linearLayout: LinearLayout,
        isBorderSet: Boolean = false,
        pos: Int
    ) {
        Log.i("Pos", "==" + pos)
        target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.i("Pos1", "==" + pos)
                imageView.setImageBitmap(bitmap)
                bitmap?.let {
                    if (isBorderSet) {
                        generatePalete(it, imageViewBg1!!, 0)
                    } else {
                        if (imageViewBg1 != null) {
                            generatePalete(it, imageViewBg1, 0)
                        }
                        if (imageViewBg2 != null) {
                            generatePalete(it, imageViewBg2, 2)
                        }
                        generatePalete(it, linearLayout, 0)
                    }
                }
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
                TODO("Not yet implemented")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                imageView.setImageResource(R.color.purple_200)
            }
        }

        Picasso.with(context)
            .load(url)
            .into(target)
        imageView.tag = target
    }
}
