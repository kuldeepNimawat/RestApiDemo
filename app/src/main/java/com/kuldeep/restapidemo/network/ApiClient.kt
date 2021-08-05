package com.kuldeep.restapidemo.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val baseUrl="https://jsonplaceholder.typicode.com/"
class ApiClient{

    companion object{
        private var retrofit: Retrofit?=null
        fun getApiClient():Retrofit{
            val gsonBuilder= GsonBuilder()
                .setLenient()
                .create()
            val okHttpClient= OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
            if(retrofit==null){
                retrofit= Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                    .build()
            }
            return retrofit!!
        }
    }
}