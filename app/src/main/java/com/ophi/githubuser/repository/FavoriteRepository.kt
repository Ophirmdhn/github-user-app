package com.ophi.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ophi.githubuser.data.local.entity.FavoriteUser
import com.ophi.githubuser.data.local.room.FavoriteDao
import com.ophi.githubuser.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getFavorite(): LiveData<List<FavoriteUser>> {
        return mFavoriteDao.getFavorite()
    }

    fun insert(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return mFavoriteDao.getFavoriteUserByUsername(username)
    }
}