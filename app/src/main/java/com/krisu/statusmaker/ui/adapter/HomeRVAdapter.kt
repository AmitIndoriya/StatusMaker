package com.krisu.statusmaker.ui.adapter

import android.R.attr.src
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.model.ImageBitmapBean
import com.krisu.statusmaker.ui.activity.EditDesignActivity
import com.krisu.statusmaker.ui.activity.HomeActivity
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class HomeRVAdapter(
    private val context: Context,
    private val arrayList: ArrayList<ImageBean>,
    private val bitmapList: ArrayList<Bitmap>,
) : RecyclerView.Adapter<HomeRVAdapter.CourseViewHolder>() {
    val palettes = ArrayList<Palette>()
    private lateinit var target: com.squareup.picasso.Target
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CourseViewHolder {
        Log.i("viewType", "==" + viewType)
        val itemView: View = if (viewType == 0) {
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_home1, parent, false
            )
        } else {
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_home1, parent, false
            )
        }
        return CourseViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        setupImage(
            arrayList[position].url,
            holder.imageView,
            holder.profileBg2,
            holder.profileName
        )
        if (Utils.getBooleanInSP(context, PreferenceConstant.IS_AVATAR_SELECTED)) {
            val avatarId = Utils.getIntInSP(context, PreferenceConstant.AVATAR_ID)
            if (avatarId != -1) {
                holder.profileImg.setImageDrawable(
                    (context as HomeActivity).viewModel.avatarListLD.value?.get(
                        avatarId
                    )?.drawable
                )
            }

        } else {
            if (!TextUtils.isEmpty(Utils.getStringInSP(context, PreferenceConstant.PROFILE_IMG))) {
                Picasso.with(context)
                    .load(Utils.getStringInSP(context, PreferenceConstant.PROFILE_IMG))
                    .into(holder.profileImg)
            } else {
                holder.profileImg.setImageDrawable(context.resources.getDrawable(R.drawable.ic_profile))
            }
        }

        holder.editTv.setOnClickListener {
            val intent = Intent(context, EditDesignActivity::class.java)
            intent.putExtra("imgUrl", arrayList[position].url)
            intent.putExtra("type", position)
            context.startActivity(intent)
        }
        /* holder.shareTv.setOnClickListener {
             (context as BaseActivity).viewToImage(holder.container, "")
         }*/
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        val pos = position % 2
        return when (pos) {
            0 -> {
                0
            }

            1 -> {
                0
            }

            else -> -1
        }
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImg: ImageView = itemView.findViewById(R.id.profile_img)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val profileBg1: ImageView = itemView.findViewById(R.id.profile_bg1)
        val profileBg2: ImageView = itemView.findViewById(R.id.profile_bg2)
        val profileName: TextView = itemView.findViewById(R.id.profile_name)
        val shareTv: TextView = itemView.findViewById(R.id.share_tv)
        val editTv: TextView = itemView.findViewById(R.id.edit_tv)
        val container: RelativeLayout = itemView.findViewById(R.id.container)
    }

    private fun generatePalete(bitmap: Bitmap, imageView: View, bgNo: Int) {
        //val icon = BitmapFactory.decodeResource(context.resources, drawable)
        /*   val url = URL(urlStr)
           val icon = BitmapFactory.decodeStream(url.openConnection().getInputStream())*/

        val palette = Palette.from(bitmap).generate()
        if (bgNo == 1) {
            setupColorProfileBg(
                palette.darkVibrantSwatch, imageView as ImageView, R.drawable.rect_rounded_bg2
            )
        } else if (bgNo == 2) {
            setupColorProfileBg(
                palette.vibrantSwatch, imageView as ImageView, R.drawable.rect_rounded_bg2
            )
        } else if (bgNo == 3) {
            setupColorProfileText(
                palette.vibrantSwatch, imageView as TextView, R.drawable.rect_rounded_bg1
            )
        }

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

    private fun setupColorProfileText(
        swatch: Palette.Swatch?, textView: TextView, drawable: Int
    ) {
        val color = swatch?.rgb ?: ContextCompat.getColor(context, R.color.white)
        val generatedColor = generateTransparentColor(color, 0.4)
        val unwrappedDrawable = AppCompatResources.getDrawable(context, drawable)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, generatedColor)
        textView.setBackgroundDrawable(wrappedDrawable)
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

    private fun setupImage(
        url: String,
        imageView: ImageView,
        imageView1: ImageView,
        textView: TextView
    ) {

        target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                imageView.setImageBitmap(bitmap)
                bitmap?.let {
                    generatePalete(it, imageView1, 1)
                    generatePalete(it, textView, 3)
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
    }
}
