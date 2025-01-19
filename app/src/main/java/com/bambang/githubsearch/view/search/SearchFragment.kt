package com.bambang.githubsearch.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    @Inject
    lateinit var userLocalModel: UserLocalModel
    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels()
    lateinit var binding: FragmentSearchBinding
    private val adapter = UserListAdapter {
//        findNavController().navigate(
//
//        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
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
