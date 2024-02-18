package com.example.youtube.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.data.repository.YouTubeRepository
import com.example.youtube.utils.Resource

class PlaylistViewModel(
    private val repository: YouTubeRepository
) : ViewModel() {

    val loadingProgressBar = MutableLiveData<Boolean>()

    fun getPlaylistItems(id: String): LiveData<Resource<PlaylistsModel>> {
        return repository.getPlaylistItems(playlistId = id)
    }
}