package com.example.youtube.data.repository

import androidx.lifecycle.LiveData
import com.example.youtube.BuildConfig
import com.example.youtube.data.api.YouTubeApiService
import com.example.youtube.data.model.PlaylistItemsModel
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.utils.Constants
import com.example.youtube.utils.Resource

class YouTubeRepository(
    private val service: YouTubeApiService
) {

    fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        service.getPlaylists(
            apiKey = BuildConfig.API_KEY,
            part = Constants.PART,
            channelId = Constants.CHANNEL_ID,
            maxResults = Constants.MAX_RESULTS
        )
    }

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItemsModel>> {
        service.getPlaylistItems(
            apiKey = BuildConfig.API_KEY,
            part = Constants.PART,
            playlistId = playlistId,
            maxResults = Constants.MAX_RESULTS
        )
    }
}