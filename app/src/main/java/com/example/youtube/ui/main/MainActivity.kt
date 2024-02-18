package com.example.youtube.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.ui.playlist.PlaylistActivity
import com.example.youtube.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = MainAdapter(this::onClick)
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMain.adapter = adapter

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getPlaylists().observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> viewModel.loadingProgressBar.postValue(true)
                is Resource.Error -> viewModel.loadingProgressBar.postValue(false)
                is Resource.Success -> {
                    viewModel.loadingProgressBar.postValue(false)
                    resource.data?.let { adapter.submitList(it.items) }
                }
            }
        }

        viewModel.loadingProgressBar.observe(this) { load ->
            binding.progressBar.visibility = if (load) View.VISIBLE else View.GONE
        }
    }

    private fun onClick(item: PlaylistsModel.Item) {
        val intent = Intent(this, PlaylistActivity::class.java).apply {
            putExtra("id", item.id)
            putExtra("title", item.snippet?.title)
            putExtra("description", item.snippet?.description)
        }
        startActivity(intent)
    }
}