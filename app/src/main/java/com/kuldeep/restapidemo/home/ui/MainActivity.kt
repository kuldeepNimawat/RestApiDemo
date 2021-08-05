package com.kuldeep.restapidemo.home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuldeep.restapidemo.R
import com.kuldeep.restapidemo.home.data.PostModel
import com.kuldeep.restapidemo.home.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {
    lateinit private var viewModel: HomeViewModel
    lateinit private  var adapter: HomeAdapter
    lateinit var rv_home : RecyclerView
    lateinit var progress_home: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_home=findViewById(R.id.rv_home)
        progress_home=findViewById(R.id.progress_home)

        viewModel= ViewModelProvider(this).get(HomeViewModel::class.java)
        initAdapter()

        viewModel.fetchAllPost()
        viewModel.postModelLiveData?.observe(this, Observer {
            if(it!=null){
                rv_home.visibility= View.VISIBLE
                adapter.setData(it as ArrayList<PostModel>)
            }else{
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            }
            progress_home.visibility = View.GONE
        })
    }

    private fun initAdapter(){
        adapter= HomeAdapter()
        rv_home.layoutManager=LinearLayoutManager(this)
        rv_home.adapter=adapter
    }
}