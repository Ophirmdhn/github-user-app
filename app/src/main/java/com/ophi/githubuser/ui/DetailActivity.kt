package com.ophi.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ophi.githubuser.R
import com.ophi.githubuser.adapter.SectionPagerAdapter
import com.ophi.githubuser.data.response.DetailUserResponse
import com.ophi.githubuser.databinding.ActivityDetailBinding
import com.ophi.githubuser.model.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

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
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}