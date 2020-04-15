package com.adsoft.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class GitHubUserData(
    @SerializedName("login") val gitHubUserLogin: String,
    @SerializedName("avatar_url") val gitHubUserAvatarUrl: String,
    @SerializedName("html_url") val gitHubUserUrlToAccount: String,
    @SerializedName("type") val gitHubUserType: String,
    @SerializedName("name") val githubUserName: String,
    @SerializedName("bio") val gitHubUserBio: String?,
    @SerializedName("public_repos") val gitHubUserPublicReposNumber: Int,
    @SerializedName("followers") val gitHubUserFollowers: Int,
    @SerializedName("following") val gitHubUserFollowing: Int,
    @SerializedName("created_at") val gitHubUserAccountCreatedDate: String,
    @SerializedName("updated_at") val gitHubUserAccountLastUpdateDate: String
)