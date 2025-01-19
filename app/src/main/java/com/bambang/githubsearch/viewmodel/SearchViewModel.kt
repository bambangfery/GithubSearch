package com.bambang.githubsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambang.githubsearch.data.UserLocalModel
import com.bambang.githubsearch.data.UserRemoteModel
import com.bambang.githubsearch.data.entity.User
import com.bambang.githubsearch.ext.SearchRestored
import com.bambang.githubsearch.ext.StateError
import com.bambang.githubsearch.ext.StateIdle
import com.bambang.githubsearch.ext.StateLiveData
import com.bambang.githubsearch.ext.StateProgress
import com.bambang.githubsearch.ext.UsersLoaded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userLocalModel: UserLocalModel,
    private val userRemoteModel: UserRemoteModel
) : ViewModel() {

    val state = StateLiveData(StateIdle)

    fun restoreLastQuery() {
        if (state.value is SearchRestored || state.value is UsersLoaded) return
        viewModelScope.launch {
            // Set state to show progress
            state.postValue(StateProgress)
            try {
                val query = withContext(Dispatchers.IO) {
                    userLocalModel.getLastQuery()
                }
                if (query.isNotEmpty()) {
                    state.postValue(SearchRestored(query))
                } else {
                    state.postValue(StateIdle)
                }
            } catch (e: Exception) {
                state.postValue(StateError(e))
            }
        }
    }

    fun search(query: String) {
        val currentValue = state.value
        if (currentValue is UsersLoaded && currentValue.query == query) return

        viewModelScope.launch {
            try {
                state.value = StateProgress

                // Mendapatkan data lokal
                val localData = getLocalData(query)
                if (localData.isNotEmpty()) {
                    state.value = UsersLoaded(query, localData)
                    return@launch
                }

                // Jika data lokal kosong, ambil data dari remote
                val remoteData = getRemoteData(query)
                state.value = UsersLoaded(query, remoteData)
            } catch (e: Exception) {
                state.value = StateError(e)
            }
        }
    }

    private suspend fun getLocalData(query: String): List<User> {
        val users = userLocalModel.getUsers(query) // Memanggil data dari database lokal
        return if (users.isNotEmpty()) users else emptyList()
    }

    private suspend fun getRemoteData(query: String): List<User> {
        val remoteUsers = userRemoteModel.getUsers(query)
        userLocalModel.saveUsers(query, remoteUsers)
        return userLocalModel.getUsers(query)
    }

}