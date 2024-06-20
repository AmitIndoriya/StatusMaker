package com.krisu.statusmaker.ui.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.ui.activity.CreateStatusActivity
import com.krisu.statusmaker.ui.fragment.ShareStatusFragment
import com.krisu.statusmaker.utils.Utils

class FontAdapter(
    private val context: CreateStatusActivity,
    val fragment: Fragment?
) : RecyclerView.Adapter<FontAdapter.CourseViewHolder>() {
    private val fontList = arrayOf(
        "Laila-SemiBold.ttf",
        "Laila-Bold.ttf",
        "Laila-Medium.ttf",
        "Laila-Light.ttf",
        "Laila-Regular.ttf",
        "Amita-Bold.ttf",
        "Amita-Regular.ttf",
        "ArchivoBlack-Regular.ttf",
        "Gagalin-Regular.otf",
        "Kalam-Bold.ttf",
        "Kalam-Regular.ttf",
        "Mukta-Medium.ttf",
        "Nexa-Rust.otf",
        "Nexa-Rust-Shadow.otf",
        "NotoSans-Bold.ttf",
        "NotoSans-Regular.ttf",
        "Poppins-Bold.ttf",
        "Poppins-Light.ttf",
        "Poppins-Medium.ttf",
        "Poppins-Regular.ttf",
        "Poppins-SemiBold.ttf",
        "Retroica.ttf",
        "Roboto-Bold.ttf",
        "Roboto-Medium.ttf",
        "Roboto-Regular.ttf",
        "Shrikhand-Regular.ttf",
        "Times-New-Roman-Bold-Italic.ttf",
        "weddingdaypersonaluseregular.ttf",
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_font,
            parent, false
        )
        return CourseViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: CourseViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.textView.typeface = Utils.getCustomFont(context, fontList[position])
        val params: LinearLayout.LayoutParams =
            holder.textView.layoutParams as LinearLayout.LayoutParams
        if (context.selectedFontNum == position) {
            holder.textView.setBackgroundDrawable(context.resources.getDrawable(R.drawable.rect_rounded5_with_green_stroke))
        } else {
            holder.textView.setBackgroundDrawable(context.resources.getDrawable(R.drawable.rect_rounded5_with_gray_stroke))

        }
        when (position) {
            0 -> {

                params.setMargins(
                    convertDpToPx(context.resources, 12),
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 6),
                    convertDpToPx(context.resources, 6)
                )

            }

            fontList.size - 1 -> {
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
        holder.textView.setOnClickListener {
            context.selectedFontNum = position
            context.selectedFont = fontList[position]
            if (fragment is ShareStatusFragment) {
                fragment.changeFont(fontList[position])
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return fontList.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: LinearLayout = itemView.findViewById(R.id.root)
        val textView: TextView = itemView.findViewById(R.id.text_view)
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