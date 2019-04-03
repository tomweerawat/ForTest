package com.example.myinterface.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myinterface.Model.Dessert
import com.example.myinterface.R
import com.example.myinterface.databinding.ItemDessertBinding
import com.example.myinterface.ui.DessertViewModel

class DessertListAdapter : RecyclerView.Adapter<DessertListAdapter.ViewHolder>() {

    private lateinit var postList: List<Dessert>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DessertListAdapter.ViewHolder {
        val binding: ItemDessertBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_dessert, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DessertListAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return if (::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(postList: List<Dessert>) {
        this.postList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemDessertBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = DessertViewModel()

        fun bind(post: Dessert) {
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}