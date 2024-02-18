package com.example.youtube.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.data.repository.YouTubeRepository
import com.example.youtube.utils.Resource

class MainViewModel(
    private val repository: YouTubeRepository
) : ViewModel() {

    val loadingProgressBar = MutableLiveData<Boolean>()

    fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        return repository.getPlaylists()
    }
}