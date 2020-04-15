package com.adsoft.githubchallenge.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user_data")
    suspend fun getLikedUsers(): List<UserDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikedUser(userDatabaseModel: UserDatabaseModel)
}