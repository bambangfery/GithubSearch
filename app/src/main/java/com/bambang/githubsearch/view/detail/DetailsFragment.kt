package com.bambang.githubsearch.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bambang.githubsearch.MainActivity
import com.bambang.githubsearch.R
import com.bambang.githubsearch.databinding.FragmentDetailsBinding
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
import com.bambang.githubsearch.ext.progress
import com.bambang.githubsearch.ext.showError
import com.bambang.githubsearch.view.search.QUERY_KEY
import com.bambang.githubsearch.view.search.QueryTextListener
import com.bambang.githubsearch.view.search.UserListAdapter
import com.bambang.githubsearch.viewmodel.SearchViewModel
import javax.inject.Inject

const val USER_ID_KEY = "USER_ID_KEY"

class DetailsFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: DetailsViewModel
    lateinit var binding: FragmentDetailsBinding
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
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_details,
            container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            repoList.initList(adapter, LinearLayoutManager.VERTICAL)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).mainComponent.inject(this)
//        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
//        viewModel.userId = arguments?.getInt(USER_ID_KEY) ?: savedInstanceState?.getInt(USER_ID_KEY) ?: 0
//        if (savedInstanceState == null) {
//            viewModel.restoreLastQuery()
//        } else {
//            savedInstanceState.getString(QUERY_KEY)?.let { viewModel.search(it) }
//        }
    }

    private fun onStateChanged(state: ViewModelState?) =
        binding.apply {
//            when (state) {
//                is StateProgress -> incView.flipper.progress()
//                is SearchRestored -> searchView.setQuery(state.query, true)
//                is UsersLoaded -> setUsers(state.users)
//                is StateError -> showError(state.throwable, R.string.error_message_search) { incView.flipper.empty() }
//                else -> incView.flipper.empty()
//            }
        }
}