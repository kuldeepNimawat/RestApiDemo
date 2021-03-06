package com.kuldeep.restapidemo.home.data

import android.graphics.PostProcessor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kuldeep.restapidemo.network.ApiClient
import com.kuldeep.restapidemo.network.ApiInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {
    fun fetchAllPost() :MutableLiveData<List<PostModel>>{
        val data= MutableLiveData<List<PostModel>>()
        val apiInterface: ApiInterface= ApiClient.getApiClient().create(ApiInterface::class.java)
        apiInterface.fetchAllPost().enqueue(object : Callback<List<PostModel>> {
            override fun onFailure(call: Call<List<PostModel>>, t : Throwable){
              data.value=null
            }
            override fun onResponse(call : Call<List<PostModel>>, response: Response<List<PostModel>>){

                val res=response.body()
                if(response.code()==200 &&  res!=null){
                    data.value=res
                }else{
                    data.value=null
                }
            }
        })
     return data
    }

    fun createPost(postModel: PostModel): LiveData<PostModel>{
        val data= MutableLiveData<PostModel>()
        val apiInterface: ApiInterface= ApiClient.getApiClient().create(ApiInterface::class.java)
        apiInterface?.createPost(postModel)?.enqueue(object : Callback<PostModel>{
            override fun onFailure(call : Call<PostModel>,t:Throwable){
                data.value=null
            }

            override fun onResponse(call : Call<PostModel>,response: Response<PostModel>)
            {
               val res=response.body()
                if(response.code()==201 && res!=null){
                    data.value=res
                }else{
                    data.value=null
                }
            }
        })
        return data
    }

    fun deletePost(id:Int):LiveData<Boolean>{
        val data= MutableLiveData<Boolean>()
        val apiInterface: ApiInterface=ApiClient.getApiClient().create(ApiInterface::class.java)
        apiInterface?.deletePost(id).enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value = false
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.code() == 200
            }
        })
        return data
    }
}