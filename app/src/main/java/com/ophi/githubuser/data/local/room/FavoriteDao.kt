package com.ophi.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ophi.githubuser.data.local.entity.FavoriteUser

@Dao
interface FavoriteDao {
    @Insert
    fun insert(favorite: FavoriteUser)

    @Delete
    fun delete(favorite: FavoriteUser)

    @Query("SELECT * FROM favoriteUser ORDER BY username ASC")
    fun getFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>
}