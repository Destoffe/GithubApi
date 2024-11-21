package com.stoffe.githubrep.repository

import com.stoffe.githubrep.api.entities.DetailedUser
import com.stoffe.githubrep.api.entities.Repository
import com.stoffe.githubrep.api.entities.SimpleUser
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {
    suspend fun getUsers(): Flow<List<SimpleUser>>

    suspend fun getUser(username: String): Flow<DetailedUser>

    suspend fun getRepositories(username: String): Flow<List<Repository>>

}