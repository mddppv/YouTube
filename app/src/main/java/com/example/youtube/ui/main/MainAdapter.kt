package com.example.youtube.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.ItemPlaylistBinding

class MainAdapter(private val onClick: (PlaylistsModel.Item) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var list = arrayListOf<PlaylistsModel.Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<PlaylistsModel.Item>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class MainViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaylistsModel.Item) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.snippet?.thumbnails?.default?.url)
                    .into(ivVideo)
                tvTitle.text = item.snippet?.title
                tvVideoCount.text = item.contentDetails?.itemCount.toString()
                llPlaylist.setOnClickListener {
                    onClick.invoke(item)
                }
            }
        }
    }
}