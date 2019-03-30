package com.example.base.recyclerview.base

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem


abstract class BaseViewHolder<Item>(itemView: View) : FastAdapter.ViewHolder<Item>(itemView)
        where Item : IItem<Item, *>