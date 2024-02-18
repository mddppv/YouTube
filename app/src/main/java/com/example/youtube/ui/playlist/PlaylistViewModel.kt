package com.example.youtube.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtube.data.model.PlaylistItemsModel
import com.example.youtube.data.repository.YouTubeRepository
import com.example.youtube.utils.Resource

class PlaylistViewModel(
    private val repository: YouTubeRepository
) : ViewModel() {

    val loadingProgressBar = MutableLiveData<Boolean>()

    fun getPlaylistItems(id: String): LiveData<Resource<PlaylistItemsModel>> {
        return repository.getPlaylistItems(playlistId = id)
    }
}