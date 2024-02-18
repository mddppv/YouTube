package com.example.youtube.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.ui.playlist.PlaylistActivity
import com.example.youtube.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMain.adapter = MainAdapter { item -> onClick(item) }

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getPlaylists().observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> viewModel.loadingProgressBar.postValue(true)
                is Resource.Error -> viewModel.loadingProgressBar.postValue(false)
                is Resource.Success -> {
                    viewModel.loadingProgressBar.postValue(false)
                    resource.data?.let {
                        (binding.rvMain.adapter as? MainAdapter)?.submitList(it.items as List<PlaylistsModel.Item>)
                    }
                }
            }
        }
    }

    private fun onClick(item: PlaylistsModel.Item) {
        val intent = Intent(this, PlaylistActivity::class.java).apply {
            putExtra("id", item.id)
            putExtra("title", item.snippet?.title)
            putExtra("description", item.snippet?.description)
            putExtra("count", item.contentDetails?.itemCount)
        }
        startActivity(intent)
    }
}