package com.krisu.statusmaker.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.ImageBean
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.fragment.ChangeBackgroundFragment
import com.krisu.statusmaker.ui.fragment.CreateStatusFragment
import com.squareup.picasso.Picasso
import java.util.Random

class ImageAdapter(
    val context: Context, val fragment: Fragment, private val imageList: ArrayList<ImageBean>
) : RecyclerView.Adapter<ImageAdapter.MyView>() {
    private val distinctNumbers: IntArray

    init {
        distinctNumbers = generateDistinctNumbers()
    }

    private fun generateDistinctNumbers(): IntArray {
        val numbers = (0 until imageList.size).toMutableList()
        numbers.shuffle(Random(System.currentTimeMillis()))
        return numbers.take(imageList.size).toIntArray()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_image_adapter, parent, false)
        val params = itemView.layoutParams as GridLayoutManager.LayoutParams
        params.height = parent.measuredWidth / 2
        itemView.layoutParams = params
        return MyView(itemView)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MyView, position: Int) {
        Log.i("random", "" + distinctNumbers[position])
        // Picasso.with(context).load(imageList[distinctNumbers[position]].url).into(holder.imageView)
        setupImage(imageList[distinctNumbers[position]].url, holder.imageView, holder.progressBar)
        holder.root.setOnClickListener {
            (context as CreateStatusActivity).changeBackground(imageList[distinctNumbers[position]].url)
        }
    }

    override fun getItemCount(): Int {
        return distinctNumbers.size
    }

    class MyView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: RelativeLayout = itemView.findViewById(R.id.root)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val progressBar: CardView = itemView.findViewById(R.id.progressbar)
    }

    private fun setupImage(
        url: String,
        imageView: ImageView,
        progressBarCardView: CardView
    ) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                imageView.setImageBitmap(bitmap)
                progressBarCardView.visibility = View.GONE
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
                imageView.setImageResource(R.color.purple_200)
                progressBarCardView.visibility = View.GONE
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                imageView.setImageResource(R.color.purple_200)
                progressBarCardView.visibility = View.VISIBLE
            }
        }

        Picasso.with(context)
            .load(url)
            .into(target)
        imageView.tag = target
    }
}