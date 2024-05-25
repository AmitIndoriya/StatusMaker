package com.krisu.statusmaker.ui.adapter

import android.app.LauncherActivity.ListItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: ListItem)
}