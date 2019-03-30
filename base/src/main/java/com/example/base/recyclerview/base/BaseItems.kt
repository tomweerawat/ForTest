package com.example.base.recyclerview.base

import com.mikepenz.fastadapter.IClickable
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.items.AbstractItem

abstract class BaseItems<TModel, Item, VH>(open val model: TModel) : AbstractItem<Item, VH>()
        where Item : IItem<Item, VH>,
              Item : IClickable<Item>,
              VH : BaseViewHolder<Item> {

//    open fun withModel(model: TModel): BaseItems<*, *, *> {
//        this.model = model
//        return this
//    }

    override fun getType(): Int {
        return 0
    }
}