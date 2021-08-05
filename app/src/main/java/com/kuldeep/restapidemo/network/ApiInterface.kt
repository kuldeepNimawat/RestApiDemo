package com.kuldeep.restapidemo.network

import android.graphics.PostProcessor
import com.kuldeep.restapidemo.home.data.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("posts")
    fun fetchAllPost(): Call<List<PostModel>>
}