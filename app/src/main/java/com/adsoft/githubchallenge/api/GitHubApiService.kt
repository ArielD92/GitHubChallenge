package com.adsoft.githubchallenge.api

import com.adsoft.githubchallenge.model.GitHubUserData
import com.adsoft.githubchallenge.model.Repos
import com.adsoft.githubchallenge.model.Repository
import com.adsoft.githubchallenge.model.Users
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("users/{username}")
    fun getGitHubUserData(
        @Path("username") username: String
    ): Observable<GitHubUserData>

    @GET("search/repositories")
    fun getSearchGitHubRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Observable<Repos>

    @GET("search/users")
    fun getSearchGitHubUsers(
        @Query("q") query: String,
        @Query("sort") sort: String = "followers",
        @Query("order") order: String = "desc"
    ): Observable<Users>

    @GET("users/{username}/repos")
    fun getUserPublicRepos(
        @Path("username") username: String
    ): Observable<List<Repository>>
}