package com.adsoft.githubchallenge.api

import com.adsoft.githubchallenge.model.LoggedUserData
import com.adsoft.githubchallenge.model.Repos
import com.adsoft.githubchallenge.model.Users
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("users/{username}")
    fun getLoggedUserData(
        @Path("username") username: String
    ): Observable<LoggedUserData>

    @GET("search/repositories")
    fun getSearchGitHubRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Observable<Repos>

    @GET("search/users")
    fun getSearchGitHubUsers(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Observable<Users>
}