package com.example.youtube.ui.playlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.ItemPlaylistItemsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {
    private var list = arrayListOf<PlaylistsModel.Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<PlaylistsModel.Item>) {
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

        fun bind(item: PlaylistsModel.Item) {
            with(binding) {
                Glide.with(itemView).load(item.snippet?.thumbnails?.high?.url).into(ivVideo)
                tvVideoTitle.text = item.snippet?.title
                val formattedDate =
                    convertYouTubeDateToCustomFormat(item.snippet?.publishedAt ?: "")
                tvVideoTime.text = formattedDate
            }
        }
    }

    fun convertYouTubeDateToCustomFormat(youtubeDate: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val parsedDate = dateFormat.parse(youtubeDate)
        val currentDate = Date()
        val diffInMilliseconds = currentDate.time - parsedDate!!.time
        val diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24)

        return if (diffInDays < 1) {
            "Сегодня"
        } else if (diffInDays < 5) {
            "$diffInDays дня назад"
        } else if (diffInDays < 7) {
            "$diffInDays дней назад"
        } else {
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(parsedDate)
        }
    }
}