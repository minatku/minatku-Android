package com.bangkit.minatku.ui.modul.premium

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.minatku.Data.Modul_Dummy
import com.bangkit.minatku.R
import com.bumptech.glide.Glide

class ModulAdapter(private val list_modul: ArrayList<Modul_Dummy>) :
    RecyclerView.Adapter<ModulAdapter.UserViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var templink: String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_modul, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val (judul, description, photo) = list_modul[position]

        templink = photo
        holder.judul.text = judul
        holder.desc.text = description
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.img)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list_modul[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = list_modul.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.modul_image)
        val judul: TextView = itemView.findViewById(R.id.modul_name)
        val desc: TextView = itemView.findViewById(R.id.modul_desc)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Modul_Dummy)
    }

}