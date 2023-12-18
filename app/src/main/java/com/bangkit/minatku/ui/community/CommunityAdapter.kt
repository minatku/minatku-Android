package com.bangkit.minatku.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.minatku.Data.PostDummy
import com.bangkit.minatku.R
import com.bumptech.glide.Glide

class CommunityAdapter(private val list_community: ArrayList<PostDummy>) :
    RecyclerView.Adapter<CommunityAdapter.UserViewHolder>() {
    private lateinit var templink: String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_community, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val (namauser, descpostcom, photo, usernamecom, tanggalcom) = list_community[position]

        templink = photo
        holder.nama.text = namauser
        holder.descpost.text = descpostcom
        holder.username.text = usernamecom
        holder.tanggal.text = tanggalcom
        Glide.with(holder.itemView.context)
            .load(photo)
            .placeholder(R.drawable.placeholder_image) // Add a placeholder image
            .error(R.drawable.error_image) // Add an error image
            .circleCrop() // Make the image circular
            .into(holder.fpimg)
    }

    override fun getItemCount(): Int = list_community.size


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fpimg: ImageView = itemView.findViewById(R.id.fp_user)
        val nama: TextView = itemView.findViewById(R.id.nama_user)
        val descpost: TextView = itemView.findViewById(R.id.tv_descpost)
        val username: TextView = itemView.findViewById(R.id.tv_usernamecom)
        val tanggal: TextView = itemView.findViewById(R.id.tv_waktu)
    }


}