package com.adsoft.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class GitHubUser(
    @SerializedName("name") val gitHubUserName:String
)