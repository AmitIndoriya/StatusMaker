package com.krisu.statusmaker.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

class ImageAdapter(
    val context: Context,
    val fragment: Fragment,
    private val imageList: ArrayList<ImageBean>
) : RecyclerView.Adapter<ImageAdapter.MyView>() {
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
        Picasso.with(context).load(imageList[position].url).into(holder.imageView)
        holder.root.setOnClickListener {
            (context as CreateStatusActivity).changeBackground(imageList[position].url)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class MyView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: RelativeLayout = itemView.findViewById(R.id.root)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

}