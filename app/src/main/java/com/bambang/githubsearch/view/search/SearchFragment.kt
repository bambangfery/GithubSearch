package com.bambang.githubsearch.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bambang.githubsearch.MainActivity
import com.bambang.githubsearch.R
import com.bambang.githubsearch.data.UserLocalModel
import com.bambang.githubsearch.data.entity.User
import com.bambang.githubsearch.databinding.FragmentSearchBinding
import com.bambang.githubsearch.ext.SearchRestored
import com.bambang.githubsearch.ext.StateError
import com.bambang.githubsearch.ext.StateProgress
import com.bambang.githubsearch.ext.UsersLoaded
import com.bambang.githubsearch.ext.ViewModelFactory
import com.bambang.githubsearch.ext.ViewModelState
import com.bambang.githubsearch.ext.empty
import com.bambang.githubsearch.ext.hideKeyboard
import com.bambang.githubsearch.ext.initList
import com.bambang.githubsearch.ext.message
import com.bambang.githubsearch.ext.progress
import com.bambang.githubsearch.ext.showError
import com.bambang.githubsearch.viewmodel.SearchViewModel
import javax.inject.Inject

const val QUERY_KEY = "QUERY_KEY"

class SearchFragment : Fragment() {

    @Inject lateinit var factory: ViewModelFactory
    private lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentSearchBinding
    private val adapter = UserListAdapter {
//        findNavController().navigate(
//            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it)
//        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search,
            container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            userList.initList(adapter, LinearLayoutManager.VERTICAL)
            //
            searchView.queryHint = getString(R.string.search_hint)
            searchView.isIconifiedByDefault = false
            searchView.setOnQueryTextListener(QueryTextListener {
                viewModel.search(it)
                activity?.hideKeyboard()
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).mainComponent.inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        viewModel.state.observe(viewLifecycleOwner, Observer { onStateChanged(it) })
        if (savedInstanceState == null) {
            viewModel.restoreLastQuery()
        } else {
            savedInstanceState.getString(QUERY_KEY)?.let { viewModel.search(it) }
        }
    }

    private fun onStateChanged(state: ViewModelState?) =
        binding.apply {
            when (state) {
                is StateProgress -> incView.flipper.progress()
                is SearchRestored -> searchView.setQuery(state.query, true)
                is UsersLoaded -> setUsers(state.users)
                is StateError -> showError(state.throwable, R.string.error_message_search) { incView.flipper.empty() }
                else -> incView.flipper.empty()
        }
    }

    private fun setUsers(users: List<User>) = if (users.isEmpty()) {
        binding.incView.flipper.message()
        adapter.clearItems()
    } else {
        binding.incView.flipper.empty()
        adapter.setItems(users)
    }

}

class QueryTextListener(private val action: (String) -> Unit) : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotBlank()) action(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

}
