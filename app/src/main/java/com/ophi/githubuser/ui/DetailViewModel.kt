package com.ophi.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ophi.githubuser.data.response.DetailUserResponse
import com.ophi.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun findGithub(query: String) {
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoadingDetail.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                    Log.d("findUser", "onCreate: ${response.body()}")
                } else {
                    Log.e(TAG,"onFailure: Error yah ges ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoadingDetail.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}