package com.stoffe.githubrep.repository

import com.stoffe.githubrep.api.GithubService
import com.stoffe.githubrep.api.entities.DetailedUser
import com.stoffe.githubrep.api.entities.Repository
import com.stoffe.githubrep.api.entities.SimpleUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubRepositoryImp @Inject constructor(
    private val githubService: GithubService,
) : IGithubRepository {
    override suspend fun getUsers(): Flow<List<SimpleUser>> = flow {
        emit(githubService.getUsers())
    }.flowOn(Dispatchers.IO)

    override suspend fun getUser(username: String): Flow<DetailedUser> = flow {
        emit(githubService.getUser(username))
    }.flowOn(Dispatchers.IO)

    override suspend fun getRepositories(username: String): Flow<List<Repository>> =
        flow {
            emit(githubService.getRepos(username))
        }
            .map { repos -> repos.filter { !it.fork } }
            .flowOn(Dispatchers.IO)
}