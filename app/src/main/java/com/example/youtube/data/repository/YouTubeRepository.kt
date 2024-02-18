package com.example.youtube.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.BuildConfig
import com.example.youtube.data.api.YouTubeApiService
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.utils.Constants
import com.example.youtube.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YouTubeRepository(
    private val service: YouTubeApiService
) {

    fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        val liveData = MutableLiveData<Resource<PlaylistsModel>>()

        liveData.value = Resource.Loading()

        service.getPlaylists(
            apiKey = BuildConfig.API_KEY,
            part = Constants.PART,
            channelId = Constants.CHANNEL_ID,
            maxResults = Constants.MAX_RESULTS
        ).enqueue(
            object : Callback<PlaylistsModel> {
                override fun onResponse(
                    call: Call<PlaylistsModel>,
                    response: Response<PlaylistsModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            liveData.value = Resource.Success(data)
                        } else {
                            liveData.value = Resource.Error(response.message())
                        }
                    } else {
                        liveData.value = Resource.Error("Unknown error exception")
                    }
                }

                override fun onFailure(call: Call<PlaylistsModel>, t: Throwable) {
                    liveData.value = Resource.Error(t.message ?: "Unknown error occurred")
                }
            }
        )

        return liveData
    }

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistsModel>> {
        val liveData = MutableLiveData<Resource<PlaylistsModel>>()

        liveData.value = Resource.Loading()

        service.getPlaylistItems(
            apiKey = BuildConfig.API_KEY,
            part = Constants.PART,
            playlistId = playlistId,
            maxResults = Constants.MAX_RESULTS
        ).enqueue(
            object : Callback<PlaylistsModel> {
                override fun onResponse(
                    call: Call<PlaylistsModel>,
                    response: Response<PlaylistsModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            liveData.value = Resource.Success(data)
                        } else {
                            liveData.value = Resource.Error(response.message())
                        }
                    } else {
                        liveData.value = Resource.Error(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<PlaylistsModel>, t: Throwable) {
                    liveData.value = Resource.Error(t.message ?: "Unknown error occurred")
                }
            }
        )

        return liveData
    }
}