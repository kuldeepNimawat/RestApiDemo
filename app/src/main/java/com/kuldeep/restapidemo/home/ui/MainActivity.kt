package com.kuldeep.restapidemo.home.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuldeep.restapidemo.R
import com.kuldeep.restapidemo.home.data.PostModel
import com.kuldeep.restapidemo.home.viewmodel.HomeViewModel
import com.kuldeep.restapidemo.network.HomeListener
import kotlinx.android.synthetic.main.create_post_dialog.view.*

class MainActivity : AppCompatActivity(),HomeListener {
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
        adapter= HomeAdapter(this)
        rv_home.layoutManager=LinearLayoutManager(this)
        rv_home.adapter=adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_create_post -> showCreatePOstDialog()
        }
        return true
    }

    private fun showCreatePOstDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.create_post_dialog, null)
        dialog.setContentView(view)

        var title = ""
        var body = ""

        view.btn_submit.setOnClickListener {
            title = view.et_title.text.toString().trim()
            body = view.et_body.text.toString().trim()

            if (title.isNotEmpty() && body.isNotEmpty()){
                val postModel = PostModel()
                postModel.userId = 1
                postModel.title = title
                postModel.body = body

                viewModel.createPost(postModel)

                viewModel.createPostLiveData?.observe(this, Observer {
                    if (it!=null){
                        adapter.addData(postModel)
                        rv_home.smoothScrollToPosition(0)
                    }else{
                        Toast.makeText(this, "Cannot create post at the moment", Toast.LENGTH_SHORT).show()
                    }
                    dialog.cancel()
                })

            }else{
                Toast.makeText(this, "Please fill data carefully!", Toast.LENGTH_SHORT).show()
            }

        }

        dialog.show()

        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onItemDeleted(postModel: PostModel, position: Int) {
        postModel.id?.let { viewModel.deletePost(it) }
        viewModel.deletePostLiveData?.observe(this, Observer {
            if (it!=null){
                adapter.removeData(position)
            }else{
                Toast.makeText(this, "Cannot delete post at the moment!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}