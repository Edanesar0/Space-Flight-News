package com.edwinespejo.flightnew.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.edwinespejo.flightnew.app.BR

class GenericAdapter<T : ListItemViewModel>(@LayoutRes val layoutId: Int) :
    RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    private val items = mutableListOf<T>()
    private var inflater: LayoutInflater? = null
    private var onListItemViewClickListener: OnListItemViewClickListener? = null

    fun addItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    fun addMoreItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItemForPosition(position: Int): T {
        return this.items[position]
    }

    fun setOnListItemViewClickListener(onListItemViewClickListener: OnListItemViewClickListener) {
        this.onListItemViewClickListener = onListItemViewClickListener
    }

    fun showOrHideComponent(position: Int) {
        val itemViewModel = items[position]
        itemViewModel.isVisible = !itemViewModel.isVisible
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = items[position]
        itemViewModel.adapterPosition = position
        onListItemViewClickListener?.let { itemViewModel.onListItemViewClickListener = it }
        holder.bind(itemViewModel)
    }


    class GenericViewHolder<T : ListItemViewModel>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewModel: T) {
            binding.setVariable(BR.listItemViewModel, itemViewModel)
            binding.executePendingBindings()
        }

    }

    fun interface OnListItemViewClickListener {
        fun onClick(view: View, position: Int)

    }
}