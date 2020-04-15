package com.adsoft.githubchallenge.room

import com.adsoft.githubchallenge.model.GitHubUser

class UserRepository(private val userDao: UserDao) {
    suspend fun getLikedUsers() = userDao.getLikedUsers()

    suspend fun addLikedUser(gitHubUser: GitHubUser, accountUrl: String) =
        userDao.addLikedUser(
            UserDatabaseModel(
                userId = gitHubUser.gitHubUserId,
                userLogin = gitHubUser.gitHubUserName,
                userAvatarUrl = gitHubUser.gitHubUserAvatarUrl,
                userProfileUrl = accountUrl
            )
        )
}