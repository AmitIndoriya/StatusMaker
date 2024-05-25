package com.krisu.statusmaker.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.krisu.statusmaker.R
import com.krisu.statusmaker.model.AvatarModel
import com.krisu.statusmaker.utils.IntentConstants

class AvatarAdapter(
    private val context: Activity,
    private val hashMap: HashMap<Int, AvatarModel>
) : RecyclerView.Adapter<AvatarAdapter.MyView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_avatar, parent, false)
        return MyView(itemView)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.imageView.setImageDrawable(hashMap[position]?.drawable)
        holder.imageView.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra(IntentConstants.AVATAR_ID, position)
            context.setResult(AppCompatActivity.RESULT_OK, returnIntent)
            context.finish()
        }
    }

    override fun getItemCount(): Int {
        return hashMap.size
    }

    class MyView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }
}