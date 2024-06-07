package com.krisu.statusmaker.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.ImageBean
import com.squareup.picasso.Picasso

class ImageAdapter(
    val context: Context,
    private val imageList: ArrayList<ImageBean>
) : RecyclerView.Adapter<ImageAdapter.MyView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_image_adapter, parent, false)
        val params = itemView.layoutParams as GridLayoutManager.LayoutParams
        params.height = parent.measuredWidth / 2
        itemView.layoutParams = params
        return MyView(itemView)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MyView, position: Int) {
        Picasso.with(context).load(imageList[position].url).into(holder.imageView)
        holder.root.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class MyView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: CardView = itemView.findViewById(R.id.root)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

}