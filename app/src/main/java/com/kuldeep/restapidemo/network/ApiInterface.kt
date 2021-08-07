package com.kuldeep.restapidemo.network

import android.graphics.PostProcessor
import com.kuldeep.restapidemo.home.data.PostModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("posts")
    fun fetchAllPost(): Call<List<PostModel>>

    @POST("posts")
    fun createPost(@Body postModel: PostModel): Call<PostModel>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id")id:Int): Call<String>
}