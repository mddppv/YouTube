package com.example.youtube.ui.playlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube.data.model.PlaylistItemsModel
import com.example.youtube.databinding.ActivityPlaylistBinding
import com.example.youtube.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    private val viewModel: PlaylistViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PlaylistAdapter()

        binding.rvPlaylist.adapter = adapter

        val playlistId = intent.getStringExtra("id").toString()
        initViewModel(playlistId, adapter)
    }

    private fun initViewModel(playlistId: String, adapter: PlaylistAdapter) {
        viewModel.getPlaylistItems(playlistId).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> viewModel.loadingProgressBar.postValue(true)
                is Resource.Error -> viewModel.loadingProgressBar.postValue(false)
                is Resource.Success -> {
                    viewModel.loadingProgressBar.postValue(false)
                    resource.data?.let {
                        adapter.submitList(it.items as List<PlaylistItemsModel.Item>)
                    }
                }
            }
        }
    }
}