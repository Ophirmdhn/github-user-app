package com.ophi.githubuser.data.retrofit

import com.ophi.githubuser.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
//    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<GithubResponse>

//    @GET("users/{username}")
//    fun getDetailUser(
//        @Path("username") username: String
//    ): Call<>
}
