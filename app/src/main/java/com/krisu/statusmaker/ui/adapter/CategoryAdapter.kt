package com.krisu.statusmaker.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.CategoryBean

class CategoryAdapter(
    private val context: Context,
    private val catList: ArrayList<CategoryBean>
) : RecyclerView.Adapter<CategoryAdapter.CourseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.category_rv_item,
            parent, false
        )
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.catName.text = catList.get(position).categoryName
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catName: TextView = itemView.findViewById(R.id.cat_name)
    }

}