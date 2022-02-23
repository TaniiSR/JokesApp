package com.task.joke.ui.homemain.jokehome

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.task.joke.R
import com.task.joke.data.models.responsedtos.Joke
import com.task.joke.data.remote.microservices.jokes.responsedtos.JokeResponse
import com.task.joke.databinding.FragmentHomeBinding
import com.task.joke.databinding.LayoutJokeDetailDialogViewBinding
import com.task.joke.ui.homemain.jokehome.jokedetaildialog.JokeDetailDialogVH
import com.task.joke.utils.base.BaseNavFragment
import com.task.joke.utils.base.interfaces.BaseViewHolder
import com.task.joke.utils.base.interfaces.OnItemClickListener
import com.task.joke.utils.extensions.observe
import com.task.joke.utils.extensions.showDialog
import com.task.joke.utils.uikit.searchview.SearchView
import com.task.joke.utils.uikit.toolbarview.ToolBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseNavFragment<FragmentHomeBinding, IHome.State, IHome.ViewModel>(
        R.layout.fragment_home
    ), IHome.View, ToolBarView.OnToolBarViewClickListener, SearchView.OnSearchViewClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    var mJokeDetailDialogVH: BaseViewHolder? = null
    var mJokeDetailAlertDialog: AlertDialog? = null

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

    override val viewModel: IHome.ViewModel by viewModels<HomeVM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.tbView.setOnToolBarViewClickListener(this)
        mViewBinding.searchView.setOnToolBarViewClickListener(this)
        mViewBinding.searchView.textWatcher = viewModel.watcher
        mViewBinding.swRefresh.setOnRefreshListener(this)
        setUpRecyclerView()
        if (viewModel.jokes.value == null) viewModel.getJokeList(viewModel.amount)
    }

    private fun setUpRecyclerView() {
        viewModel.jokeListAdapter.onItemClickListener = rvItemClickListener
        mViewBinding.rvDevices.adapter = viewModel.jokeListAdapter
    }

    override fun onEndIconClicked() {
        mViewBinding.searchView.openSearch()
        mViewBinding.tbView.visibility = View.INVISIBLE
    }

    override fun onCancelClicked() {
        mViewBinding.searchView.closeSearch()
        mViewBinding.tbView.visibility = View.VISIBLE
        viewModel.jokeListAdapter.filter.filter("")
    }

    override fun onClick(id: Int) = Unit

    private val rvItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (data) {
                is Joke -> {
                    openJokeDetailDialog(data)
                }
            }
        }
    }

    private fun openJokeDetailDialog(joke: Joke) {
        mJokeDetailDialogVH =
            JokeDetailDialogVH(joke, jokeDialogViewClickListener)
        mJokeDetailDialogVH?.let { dialogVH ->
            mJokeDetailAlertDialog = requireActivity().showDialog(dialogVH, true) {
                LayoutJokeDetailDialogViewBinding.inflate(layoutInflater)
            }
        }
    }

    private val jokeDialogViewClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.ivClose -> mJokeDetailAlertDialog?.dismiss()
            }
        }
    }

    private fun handleProducts(products: JokeResponse?) {
        products?.let { prod ->
            viewModel.jokeListAdapter.setList(prod.jokes ?: listOf())
        }
    }

    override fun viewModelObservers() {
        observe(viewModel.jokes, ::handleProducts)
    }

    override fun onRefresh() {
        viewModel.getJokeList(viewModel.amount)
        mViewBinding.swRefresh.isRefreshing = false
    }
}