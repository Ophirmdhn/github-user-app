package com.ophi.githubuser.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ophi.githubuser.R
import com.ophi.githubuser.adapter.SectionPagerAdapter
import com.ophi.githubuser.data.local.entity.FavoriteUser
import com.ophi.githubuser.data.response.DetailUserResponse
import com.ophi.githubuser.databinding.ActivityDetailBinding
import com.ophi.githubuser.model.DetailViewModel
import com.ophi.githubuser.model.FavoriteViewModel
import com.ophi.githubuser.model.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private var favoriteUser: FavoriteUser? = null

    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            detailViewModel.findGithub(username)

            favoriteUser = FavoriteUser(username.toString(), null)
            favoriteViewModel.getFavoriteUserByUsername(username).observe(this) { favoriteUser ->
                if (favoriteUser != null) {
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(
                        binding.fabFavorite.context, R.drawable.ic_favorite_24
                    ))
                    isFavorite = true
                } else {
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(
                        binding.fabFavorite.context, R.drawable.ic_favorite_border_24
                    ))
                    isFavorite = false
                }
            }
        }

        supportActionBar?.hide()

        detailViewModel.detailUser.observe(this) { detail ->
            setDataUser(detail)
        }

        detailViewModel.isLoadingDetail.observe(this) {
            showLoading(it)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = intent.getStringExtra(EXTRA_USERNAME).toString()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun setDataUser(detail: DetailUserResponse) {
        binding.apply {
            Glide.with(applicationContext)
                .load(detail.avatarUrl)
                .into(detailUserPhoto)
            tvFullname.text = detail.name
            tvDetailUsername.text = detail.login
            tvFollowers.text = resources.getString(R.string.total_followers, detail.followers)
            tvFollowing.text = resources.getString(R.string.total_following, detail.following)
        }

        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                favoriteViewModel.delete(favoriteUser as FavoriteUser)
                Toast.makeText(this, "Dihapus dari Favorite", Toast.LENGTH_SHORT).show()
            } else {
                favoriteUser = FavoriteUser(detail.login, detail.avatarUrl)
                favoriteViewModel.insert(favoriteUser as FavoriteUser)
                Toast.makeText(this, "Ditambahkan ke Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}