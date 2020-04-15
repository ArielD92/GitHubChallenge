package com.adsoft.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class GitHubUser(
    @SerializedName("login") val gitHubUserName:String?,
    @SerializedName("id") val gitHubUserId: Long,
    @SerializedName("avatar_url") val gitHubUserAvatarUrl: String?
)