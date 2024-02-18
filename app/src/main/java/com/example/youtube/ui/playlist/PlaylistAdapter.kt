package com.example.youtube.ui.playlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtube.data.model.PlaylistItemsModel
import com.example.youtube.databinding.ItemPlaylistItemsBinding

class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {
    private var list = arrayListOf<PlaylistItemsModel.Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<PlaylistItemsModel.Item>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            ItemPlaylistItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class PlaylistViewHolder(private val binding: ItemPlaylistItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaylistItemsModel.Item) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.snippet?.thumbnails?.default?.url)
                    .into(ivVideo)
                tvVideoTitle.text = item.snippet?.title
                tvVideoTime.text = item.snippet?.description
            }
        }
    }
}