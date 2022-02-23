package com.task.joke.utils.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.task.joke.utils.base.interfaces.OnItemClickListener

abstract class BaseBindingSearchRecyclerAdapter<T : Any, VH : RecyclerView.ViewHolder>() :
    RecyclerView.Adapter<VH>() {

    var onItemClickListener: OnItemClickListener? = null
    var allowFullItemClickListener: Boolean = false
    lateinit var filter: ItemFilter
    private var list: MutableList<T> = mutableListOf()
    var filterCount = MutableLiveData<Int>()
    private lateinit var duplicate: MutableList<T>

    constructor(list: MutableList<T>) : this() {
        this.list = list
        duplicate = mutableListOf()
        this.duplicate.addAll(list)
        filter = ItemFilter(list)
    }

    protected abstract fun onCreateViewHolder(binding: ViewBinding): VH

    protected abstract fun getViewBindingByViewType(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = getViewBindingByViewType(layoutInflater, parent, viewType)
        return onCreateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (allowFullItemClickListener)
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(
                    it,
                    getDataForPosition(position),
                    position
                )
            }
    }

    fun getDataForPosition(position: Int): T {
        return list[position]
    }

    fun getDataList(): MutableList<T> {
        return list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
        updateLists()

    }

    private fun updateLists() {
        duplicate = mutableListOf()
        duplicate.addAll(list)
        filter = ItemFilter(list)
    }

    fun addListItem(list: T) {
        this.list.add(list)
        notifyItemInserted(this.list.size)
    }

    fun addList(list: List<T>) {
        val from = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(from, this.list.size)
    }

    fun setItemAt(position: Int, item: T) {
        this.list[position] = item
        notifyItemChanged(position)
    }

    fun removeItemAt(position: Int) {
        this.list.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAllItems() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun setItemListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    abstract inner class ViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: T) = Unit
    }

    abstract fun filterItem(constraint: CharSequence?, item: T): Boolean
    inner class ItemFilter(private val dataList: MutableList<T>) : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {

            val results = FilterResults()
            val list = mutableListOf<T>()
            list.addAll(duplicate)

            val count = list.size
            val nlist = ArrayList<T>(count)

            if (!constraint.isNullOrEmpty()) {
                for (i in 0 until count) {
                    if (filterItem(constraint, list[i])) {
                        nlist.add(list[i])
                    }
                }
            } else {
                nlist.addAll(list)
            }

            results.values = nlist
            results.count = nlist.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            list.clear()
            filterCount.value = results?.count
            list.addAll(results?.values as MutableList<T>)
            notifyDataSetChanged()
        }
    }

}