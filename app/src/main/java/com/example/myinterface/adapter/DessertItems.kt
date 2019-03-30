package com.example.myinterface.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.base.recyclerview.base.BaseItems
import com.example.base.recyclerview.base.BaseViewHolder
import com.example.myinterface.Model.DessertDao
import com.example.myinterface.R

class DessertItems(override val model: DessertDao) : BaseItems<DessertDao, DessertItems, DessertItems.ViewHolder>(model = model) {
    override fun getViewHolder(v: View): DessertItems.ViewHolder {
        return ViewHolder(v)
    }

    override fun getLayoutRes(): Int {
        return R.layout.list_dessertitems_layout
    }
    class ViewHolder(itemView: View) : BaseViewHolder<DessertItems>(itemView) {

        internal var title: TextView
        internal var imgbackground: ImageView

        init {
            title = itemView.findViewById(R.id.txtTitle) as TextView
            imgbackground = itemView.findViewById(R.id.imgbackground) as ImageView

        }

        override fun unbindView(item: DessertItems) {
          title.text = String()
        }

        override fun bindView(item: DessertItems, payloads: MutableList<Any>) {
            title.text = item.model.title
            this.setImageDetail(item.model.imageUrl)
        }

        private  fun setImageDetail(imgtype: String) {
            Glide.with(itemView)
                .load(imgtype)
                .into(imgbackground)
        }

    }
}