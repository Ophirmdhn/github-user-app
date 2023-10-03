package com.ophi.githubuser.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ophi.githubuser.data.local.entity.FavoriteUser
import com.ophi.githubuser.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        favoriteRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        favoriteRepository.delete(favoriteUser)
    }

    fun getFavorite(): LiveData<List<FavoriteUser>> = favoriteRepository.getFavorite()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return favoriteRepository.getFavoriteUserByUsername(username)
    }
}