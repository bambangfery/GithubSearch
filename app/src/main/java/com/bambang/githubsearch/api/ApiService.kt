package com.bambang.githubsearch.api

import com.bambang.githubsearch.model.ProfileResponse
import com.bambang.githubsearch.model.RepoEntry
import com.bambang.githubsearch.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * GitHub API retrofit service
 */
interface ApiService {

    @GET("search/users")
    suspend fun getUsers(@Query("q") query: String): Response<UsersResponse>

    @GET("users/{login}")
    suspend fun getProfile(@Path("login") login: String): Response<ProfileResponse>

    @GET("users/{login}/repos")
    suspend fun getRepos(@Path("login") login: String): Response<List<RepoEntry>>

}