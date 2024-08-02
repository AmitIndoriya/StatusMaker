package com.krisu.statusmaker.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.CategoryBean
import com.krisu.statusmaker.ui.activity.HomeActivity

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

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.catName.text = catList[position].categoryName
        if (position == (context as HomeActivity).selectedCategory) {
            holder.root.background =
                context.resources.getDrawable(R.drawable.rect_white_with_green_border25)
            holder.catName.setTextColor(context.resources.getColor(R.color.green_008000))
        } else {
            holder.root.background = context.resources.getDrawable(R.drawable.rectangle)
            holder.catName.setTextColor(context.resources.getColor(R.color.gray_A9A9A9))
        }
        holder.root.setOnClickListener {
            if (context.selectedCategory != position) {
                context.selectedCategory = position
                context.fetchImagesById(catList[position].categoryId,catList[position].parentCateId)
            }else{
                context.dismissCategoryBottomSheet()
            }
        }
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catName: TextView = itemView.findViewById(R.id.cat_name)
        val root: LinearLayout = itemView.findViewById(R.id.root)
    }

}