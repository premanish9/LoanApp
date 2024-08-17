package com.example.loanapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SimpleGenericRecyclerAdapter<T : Any>(private val dataSet:ArrayList<T>, @LayoutRes val layoutID: Int,private val bindingInterface: GenericSimpleRecyclerBindingInterface<T>):RecyclerView.Adapter<SimpleGenericRecyclerAdapter.GenericViewHolder>() {
    class GenericViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun <T : Any> bind(
            item: T,
            bindingInterface: GenericSimpleRecyclerBindingInterface<T>
        ) = bindingInterface.bindData(item,view)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):GenericViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutID,parent,false)
        return GenericViewHolder(view)
    }

    override fun onBindViewHolder(holderGeneric:GenericViewHolder, position: Int) {
        val item = dataSet[position]
        holderGeneric.bind(item,bindingInterface)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

interface GenericSimpleRecyclerBindingInterface<T:Any> {
    fun bindData(item: T,view:View)
}