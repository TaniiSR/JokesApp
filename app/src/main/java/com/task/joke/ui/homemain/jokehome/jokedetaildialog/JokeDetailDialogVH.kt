package com.task.joke.ui.homemain.jokehome.jokedetaildialog

import android.annotation.SuppressLint
import android.view.View
import androidx.viewbinding.ViewBinding
import com.task.joke.data.models.responsedtos.Joke
import com.task.joke.databinding.LayoutJokeDetailDialogViewBinding
import com.task.joke.ui.homemain.jokehome.adapter.JokeType
import com.task.joke.utils.base.interfaces.BaseViewHolder
import com.task.joke.utils.base.interfaces.OnItemClickListener

class JokeDetailDialogVH(
    var data: Joke,
    private val itemClickListener: OnItemClickListener,
) : BaseViewHolder {
    private var mViewBinding: ViewBinding? = null

    @SuppressLint("SetTextI18n")
    override fun onBind(viewBinding: ViewBinding?) {
        mViewBinding = viewBinding
        if (viewBinding is LayoutJokeDetailDialogViewBinding) {
            viewBinding.ivClose.setOnClickListener {
                itemClickListener.onItemClick(view = it, data, 0)
            }
            viewBinding.tvId.text = "ID: ${data.id}"
            viewBinding.tvType.text = "Type: ${data.type}"
            viewBinding.tvJoke.text = if (data.joke.isNullOrEmpty()) data.setup else data.joke
            viewBinding.tvDescription.text = data.delivery
            when (data.type) {
                JokeType.SINGLE.type -> viewBinding.tvDescription.visibility = View.GONE
                else -> viewBinding.tvDescription.visibility = View.VISIBLE
            }
        }
    }

    override fun notifyDatasetRefresh(data: Any) {
        if (data is Joke) {
            this.data = data
            onBind(mViewBinding)
        }
    }

}