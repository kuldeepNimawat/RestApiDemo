package com.kuldeep.restapidemo.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuldeep.restapidemo.home.data.HomeRepository
import com.kuldeep.restapidemo.home.data.PostModel

class HomeViewModel : ViewModel() {
    private var homeRepository: HomeRepository?=null
    var postModelLiveData: MutableLiveData<List<PostModel>>?=null
    var createPostLiveData: LiveData<PostModel>?=null
    var deletePostLiveData: LiveData<Boolean>?=null
    init{
        homeRepository= HomeRepository()
        postModelLiveData= MutableLiveData()
    }
    fun fetchAllPost(){
        postModelLiveData=homeRepository?.fetchAllPost()
    }

    fun createPost(postModel: PostModel){
        createPostLiveData = homeRepository?.createPost(postModel)
    }

    fun deletePost(id:Int){
        deletePostLiveData = homeRepository?.deletePost(id)
    }
}