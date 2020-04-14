package com.adsoft.githubchallenge.model

import com.google.gson.annotations.SerializedName

class Repos(
    @SerializedName("items") val reposList: List<Repository>
)