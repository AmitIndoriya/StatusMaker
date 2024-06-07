package com.krisu.statusmaker.ui.adapter

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.ui.activity.CreateStatusActivity


class ColorAdapter(
    private val context: CreateStatusActivity
) : RecyclerView.Adapter<ColorAdapter.CourseViewHolder>() {
    private var selectedPos: Int = 0
    private val colorList: IntArray = intArrayOf(
        R.color.color1,
        R.color.color2,
        R.color.color3,
        R.color.color4,
        R.color.color5,
        R.color.color6,
        R.color.color7,
        R.color.color8,
        R.color.color9,
        R.color.color10,
        R.color.color11,
        R.color.color12,
        R.color.color13,
        R.color.color14,
        R.color.color15,
        R.color.color16,
        R.color.color17,
        R.color.color18,
        R.color.color19,
        R.color.color20,
        R.color.color21,
        R.color.color22,
        R.color.color23,
        R.color.color24,
        R.color.color25,
        R.color.color26,
        R.color.color27,
        R.color.color28,
        R.color.color29,
        R.color.color30,
        R.color.color31,
        R.color.color32,
        R.color.color33,
        R.color.color34,
        R.color.color35,
        R.color.color36,
        R.color.color37,
        R.color.color38,
        R.color.color39,
        R.color.color40,
        R.color.color41,
        R.color.color42,
        R.color.color43,
        R.color.color44,
        R.color.color45
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_color,
            parent, false
        )
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        DrawableCompat.setTint(
            holder.imageView.drawable,
            ContextCompat.getColor(context as AppCompatActivity, colorList[position])
        )
        if (position == selectedPos) {
            holder.linearLayout.setBackgroundResource(R.drawable.circle_light_gray_red_border)
        } else {
            holder.linearLayout.setBackgroundResource(R.drawable.circle_light_gray)
        }
        holder.imageView.setOnClickListener {
            //context.changeColor(colorList[position])
        }
        val params: LinearLayout.LayoutParams =
            holder.linearLayout.layoutParams as LinearLayout.LayoutParams
        when (position) {
            0 -> {

                params.setMargins(
                    convertDpToPx(context.resources, 12),
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 6)
                )

            }

            colorList.size - 1 -> {
                params.setMargins(
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 12),
                    convertDpToPx(context.resources, 6),
                )

            }

            else -> {
                params.setMargins(
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 6),
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: LinearLayout = itemView.findViewById(R.id.root)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.layout)
    }

    private fun convertDpToPx(resources: Resources, dip: Int): Int {
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip.toFloat(),
            resources.displayMetrics
        )
        return px.toInt()
    }
}
