package com.adsoft.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name") val repositoryName: String?,
    @SerializedName("owner") val repositoryOwner: RepositoryOwner,
    @SerializedName("html_url") val repositoryUrl: String?,
    @SerializedName("language") val repositoryLanguage: String?
)