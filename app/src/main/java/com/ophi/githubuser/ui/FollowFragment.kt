package com.ophi.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ophi.githubuser.adapter.UserAdapter
import com.ophi.githubuser.data.response.ItemsItem
import com.ophi.githubuser.databinding.FragmentFollowBinding
import com.ophi.githubuser.model.DetailViewModel

class FollowFragment : Fragment() {

//    private lateinit var binding: FragmentFollowBinding

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager

        detailViewModel.isLoadingDetail.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }

        arguments?.let {
            val position = it.getInt(ARG_POSITION)
            val username = it.getString(ARG_USERNAME)

            if (position == 1) {
                detailViewModel.getFollower("$username")
                detailViewModel.followers.observe(viewLifecycleOwner) { followers ->
                    setUserData(followers)
                }
            } else {
                detailViewModel.getFollowing("$username")
                detailViewModel.following.observe(viewLifecycleOwner) { following ->
                    setUserData(following)
                }
            }
        }
    }

    private fun setUserData(detail: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(detail)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}