package com.krisu.statusmaker.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.MultiColorBean
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.fragment.MultiColorFragment


class MultiColorAdapter(
    private val context: CreateStatusActivity,
    val fragment: MultiColorFragment,
    private val hashMap: HashMap<Int, MultiColorBean>
) : RecyclerView.Adapter<MultiColorAdapter.CourseViewHolder>() {
    private val colorList = context.viewModel.getColorList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_multi_color,
            parent, false
        )
        return CourseViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(
        holder: CourseViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.textView.text = "" + hashMap[position]!!.start + "-" + hashMap[position]!!.end
        holder.textView.layoutParams as RelativeLayout.LayoutParams
        DrawableCompat.setTint(
            holder.textColor.drawable,
            ContextCompat.getColor(
                context as AppCompatActivity,
                colorList[hashMap[position]!!.textColor]
            )
        )
        DrawableCompat.setTint(
            holder.shadowColor.drawable,
            ContextCompat.getColor(
                context as AppCompatActivity,
                colorList[hashMap[position]!!.shadowColor]
            )
        )
        holder.editIv.setOnClickListener {
            fragment.openDialog(hashMap[position])
        }

    }

    override fun getItemCount(): Int {
        return hashMap.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: RelativeLayout = itemView.findViewById(R.id.root)
        val textView: TextView = itemView.findViewById(R.id.range_tv)
        val textColor: ImageView = itemView.findViewById(R.id.text_color_iv)
        val shadowColor: ImageView = itemView.findViewById(R.id.shadow_color_iv)
        val editIv: ImageView = itemView.findViewById(R.id.edit_iv)
    }

}