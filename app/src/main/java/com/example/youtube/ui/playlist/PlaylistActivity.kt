package com.example.youtube.ui.playlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube.databinding.ActivityPlaylistBinding
import com.example.youtube.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    private val adapter = PlaylistAdapter()
    private val viewModel: PlaylistViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPlaylist.adapter = adapter
        binding.btnBack.setOnClickListener { onBackPressed() }

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
                        adapter.submitList(it.items)
                    }
                }
            }
        }

        viewModel.loadingProgressBar.observe(this) { load ->
            binding.progressBar.visibility = if (load) View.VISIBLE else View.GONE
        }
    }
}