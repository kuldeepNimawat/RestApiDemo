package com.kuldeep.restapidemo.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuldeep.restapidemo.R
import com.kuldeep.restapidemo.home.data.PostModel
import com.kuldeep.restapidemo.network.HomeListener
import kotlinx.android.synthetic.main.home_rv_item_view.view.*

class HomeAdapter(var listener:HomeListener): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    private var data : ArrayList<PostModel>?=null

    fun setData(list: ArrayList<PostModel>){
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_rv_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.itemView.img_delete.setOnClickListener {
            item?.let { it ->
                listener?.onItemDeleted(it, position)
            }
        }
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(item: PostModel?) {
            itemView.findViewById<TextView>(R.id.tv_home_item_title).text = item?.title
            itemView.findViewById<TextView>(R.id.tv_home_item_body).text = item?.body
        }

    }

    fun addData(postModel: PostModel) {
        data?.add(0,postModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }
}