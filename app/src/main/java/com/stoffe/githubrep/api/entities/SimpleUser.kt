package com.stoffe.githubrep.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SimpleUser(
    @Json(name = "avatar_url")
    val avatarUrl: String,
    val login: String,
)