package com.ophi.githubuser.ui

import android.annotation.SuppressLint
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.ophi.githubuser.R
import com.ophi.githubuser.data.response.DetailUserResponse
import com.ophi.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) detailViewModel.findGithub(username)

        supportActionBar?.hide()

        detailViewModel.detailUser.observe(this) { detail ->
            setDataUser(detail)
        }

        detailViewModel.isLoadingDetail.observe(this) {
            showLoading(it)
        }
    }
    private fun setDataUser(detail: DetailUserResponse) {
        binding.apply {
            Glide.with(applicationContext)
                .load(detail.avatarUrl)
                .into(detailUserPhoto)
            tvFullname.text = detail.name
            tvDetailUsername.text = detail.login
            tvFollowers.text = "${detail.followers} Followers"
            tvFollowing.text = "${detail.following} Following"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}