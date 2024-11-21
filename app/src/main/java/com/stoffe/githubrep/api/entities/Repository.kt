package com.stoffe.githubrep.api.entities

import com.squareup.moshi.Json

data class Repository(
    @Json(name = "html_url")
    val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    @Json(name = "full_name")
    val fullName: String,
    val id: Int,
    val language: String?,
    val name: String,
    @Json(name = "stargazers_count")
    val stargazersCount: Int,
)


