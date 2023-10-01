package com.ophi.githubuser.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ophi.githubuser.adapter.FavoriteAdapter
import com.ophi.githubuser.databinding.ActivityFavoriteBinding
import com.ophi.githubuser.model.FavoriteViewModel
import com.ophi.githubuser.model.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.layoutManager = layoutManager

        favoriteViewModel.getFavorite().observe(this) { favoriteUser ->
            if (favoriteUser != null) {
                val adapter = FavoriteAdapter()
                adapter.submitList(favoriteUser.toMutableList())
                binding?.rvFavorite?.adapter = adapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}