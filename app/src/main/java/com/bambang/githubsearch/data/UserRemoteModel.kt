package com.bambang.githubsearch.data

import com.bambang.githubsearch.api.ApiService
import com.bambang.githubsearch.model.ProfileResponse
import com.bambang.githubsearch.model.RepoEntry
import com.bambang.githubsearch.model.UserEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provide User data from [ApiService]
 */
@Singleton
class UserRemoteModel @Inject constructor() {

    @Inject lateinit var apiService: ApiService

    suspend fun getUsers(query: String): List<UserEntry> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getUsers(query)
            if (response.isSuccessful) {
                response.body()?.items ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    suspend fun getProfile(login: String): ProfileResponse {
        return withContext(Dispatchers.IO) {
            val response = apiService.getProfile(login)
            if (response.isSuccessful) {
                response.body() ?: throw RuntimeException("Profile not found")
            } else {
                throw RuntimeException("Unable to get Profile info")
            }
        }
    }

    suspend fun getRepos(login: String): List<RepoEntry> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getRepos(login)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw RuntimeException("Unable to get Repositories info")
            }
        }
    }

}