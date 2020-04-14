package com.adsoft.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class RepositoryOwner(
    @SerializedName("login") val ownerLogin: String?,
    @SerializedName("html_url") val ownerUrl: String,
    @SerializedName("avatar_url") val ownerAvatarUrl: String
)