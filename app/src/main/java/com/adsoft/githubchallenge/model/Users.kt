package com.adsoft.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("items") val usersList: List<GitHubUser>
)