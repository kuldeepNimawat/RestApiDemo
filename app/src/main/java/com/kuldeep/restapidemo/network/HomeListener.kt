package com.kuldeep.restapidemo.network

import com.kuldeep.restapidemo.home.data.PostModel

interface HomeListener {
    fun onItemDeleted(postModel: PostModel, position: Int)
}