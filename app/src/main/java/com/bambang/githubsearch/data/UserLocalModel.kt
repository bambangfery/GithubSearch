package com.bambang.githubsearch.data

import com.bambang.githubsearch.data.dao.QueriesDao
import com.bambang.githubsearch.data.dao.Query2UserDao
import com.bambang.githubsearch.data.dao.RepoDao
import com.bambang.githubsearch.data.dao.UserDao
import com.bambang.githubsearch.data.entity.Queries
import com.bambang.githubsearch.data.entity.Query2User
import com.bambang.githubsearch.data.entity.Repo
import com.bambang.githubsearch.data.entity.User
import com.bambang.githubsearch.model.ProfileResponse
import com.bambang.githubsearch.model.RepoEntry
import com.bambang.githubsearch.model.UserEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalModel @Inject constructor(
    private val userDao: UserDao,
    private val queriesDao: QueriesDao,
    private val query2UserDao: Query2UserDao,
    private val repoDao: RepoDao
) {

    suspend fun saveUsers(query: String, users: List<UserEntry>) {
        if (users.isEmpty()) return

        userDao.runInTransaction {
            val dbQuery = saveQuery(query)
            val userIds = saveUsers(users)
            updateQuery2Users(dbQuery, userIds)
        }
    }

     fun getUsers(query: String): List<User> {
        val dbQuery = queriesDao.findQueryByText(query) ?: return emptyList()
        queriesDao.insertOrReplace(dbQuery.apply { execDate = System.currentTimeMillis() })
        return userDao.findUsersByQuery(query)
    }

     fun getLastQuery(): String {
        return queriesDao.getLastQueries()?.que ?: ""
    }

     fun saveProfile(profile: ProfileResponse) {
        val user = userDao.findUserByServerId(profile.id) ?: return
        userDao.insertOrReplace(user.apply {
            name = profile.name
            bio = profile.bio
        })
    }

    suspend fun saveRepos(user: User, repos: List<RepoEntry>) {
        if (repos.isEmpty()) return
        userDao.runInTransaction {
            saveRepos(user.id, repos)
        }
    }

    suspend fun clear() {
        userDao.runInTransaction {
            userDao.deleteAll()
            queriesDao.deleteAll()
            query2UserDao.deleteAll()
            repoDao.deleteAll()
        }
    }

    private suspend fun saveQuery(query: String): Queries {
        val dbQuery = queriesDao.findQueryByText(query)
        return if (dbQuery == null) {
            Queries(que = query, execDate = System.currentTimeMillis()).also {
                queriesDao.insertOrReplace(it)
            }
        } else {
            dbQuery.apply { execDate = System.currentTimeMillis() }
                .also { queriesDao.insertOrReplace(it) }
        }
    }

    private suspend fun saveUsers(users: List<UserEntry>): List<Long> {
        return users.map {
            val dbUser = userDao.findUserByServerId(it.id) ?: User(
                serverId = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                name = "",
                bio = ""
            )
            dbUser.apply {
                login = it.login
                avatarUrl = it.avatarUrl
            }
            userDao.insertOrReplace(dbUser)
        }
    }

    private suspend fun saveRepos(userId: Long, repos: List<RepoEntry>) {
        repos.forEach {
            val dbRepo = repoDao.findRepoByPK(it.id) ?: Repo(
                serverId = it.id,
                name = it.name,
                language = it.language,
                description = it.description,
                userId = userId
            )
            dbRepo.apply {
                name = it.name
                language = it.language
                description = it.description
                this.userId = userId
            }
            repoDao.insertOrReplace(dbRepo)
        }
    }

    private suspend fun updateQuery2Users(query: Queries, userIds: List<Long>) {
        query2UserDao.deleteByQueryId(query.id)
        userIds.forEach {
            val dbItem = Query2User(queryId = query.id, userId = it)
            query2UserDao.insert(dbItem)
        }
    }
}