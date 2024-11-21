package com.stoffe.githubrep.api.entities

import com.squareup.moshi.Json

data class DetailedUser(
    @Json(name = "avatar_url")
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    @Json(name = "html_url")
    val htmlUrl: String,
    val login: String,
    val name: String?,
    @Json(name = "repos_url")
    val reposUrl: String,
)