package com.stoffe.githubrep.api

import com.stoffe.githubrep.api.entities.DetailedUser
import com.stoffe.githubrep.api.entities.Repository
import com.stoffe.githubrep.api.entities.SimpleUser
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users")
    suspend fun getUsers(): List<SimpleUser>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): DetailedUser

    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): List<Repository>
}