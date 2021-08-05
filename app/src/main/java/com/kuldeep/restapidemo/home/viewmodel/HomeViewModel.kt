package com.kuldeep.restapidemo.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuldeep.restapidemo.home.data.HomeRepository
import com.kuldeep.restapidemo.home.data.PostModel

class HomeViewModel : ViewModel() {
    private var homeRepository: HomeRepository?=null
    var postModelLiveData: MutableLiveData<List<PostModel>>?=null
    init{
        homeRepository= HomeRepository()
        postModelLiveData= MutableLiveData()
    }
    fun fetchAllPost(){
        postModelLiveData=homeRepository?.fetchAllPost()
    }
}