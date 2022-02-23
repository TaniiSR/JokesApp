package com.task.joke.ui.homemain.jokehome.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.task.joke.R
import com.task.joke.data.models.responsedtos.Joke
import com.task.joke.databinding.LayoutJokeListAvailableItemBinding
import com.task.joke.databinding.LayoutNoJokeResultItemBinding
import com.task.joke.utils.base.BaseBindingSearchRecyclerAdapter
import com.task.joke.utils.base.interfaces.OnItemClickListener
import java.util.*

class JokeListAdapter(
    private val list: MutableList<Joke>,
) : BaseBindingSearchRecyclerAdapter<Joke, JokeListAdapter.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewBinding): ViewHolder {
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (list.isNullOrEmpty()) holder.onBind(
            Joke(),
            position,
            onItemClickListener
        ) else
            holder.onBind(list[position], position, onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (list.isNullOrEmpty())
            R.layout.layout_no_joke_result_item
        else R.layout.layout_joke_list_available_item
    }


    inner class ViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: Joke,
            position: Int,
            onItemClickListener: OnItemClickListener?
        ) {
            when (binding) {
                is LayoutNoJokeResultItemBinding -> {
                    handleNoJokesResultBinding(binding)
                }
                is LayoutJokeListAvailableItemBinding -> {
                    handleSearchBinding(binding, data, position, onItemClickListener)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleSearchBinding(
        binding: LayoutJokeListAvailableItemBinding,
        data: Joke,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        binding.tvJoke.text = if (data.joke.isNullOrEmpty()) data.setup else data.joke
        binding.tvDescription.text = data.delivery
        when (data.type) {
            JokeType.SINGLE.type -> binding.tvDescription.visibility = View.GONE
            else -> binding.tvDescription.visibility = View.VISIBLE
        }
        binding.clMainItem.setOnClickListener {
            onItemClickListener?.onItemClick(it, data, position)
        }

    }

    private fun handleNoJokesResultBinding(
        binding: LayoutNoJokeResultItemBinding
    ) = Unit

    override fun getItemCount(): Int = if (list.isNullOrEmpty()) 1 else list.size

    override fun filterItem(constraint: CharSequence?, item: Joke): Boolean {
        val filterString = constraint.toString().lowercase(Locale.ROOT)
        val deviceName = item.category?.lowercase(Locale.ROOT) ?: ""
        return deviceName.startsWith(filterString) || deviceName.contains(filterString)
    }

    override fun getViewBindingByViewType(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return if (viewType == R.layout.layout_no_joke_result_item)
            LayoutNoJokeResultItemBinding.inflate(layoutInflater, viewGroup, false)
        else LayoutJokeListAvailableItemBinding.inflate(layoutInflater, viewGroup, false)
    }
}