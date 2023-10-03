package com.ophi.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ophi.githubuser.data.local.entity.FavoriteUser
import com.ophi.githubuser.databinding.ItemRowUserBinding
import com.ophi.githubuser.ui.DetailActivity

class FavoriteAdapter: ListAdapter<FavoriteUser, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favUser = getItem(position)
        holder.bind(favUser)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("extra_username", favUser.username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    inner class MyViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favUser: FavoriteUser) {
            Glide.with(binding.root)
                .load(favUser.avatarUrl)
                .into(binding.userPhoto)
            binding.tvUsername.text = favUser.username
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem.username == newItem.username
            }
        }
    }
}