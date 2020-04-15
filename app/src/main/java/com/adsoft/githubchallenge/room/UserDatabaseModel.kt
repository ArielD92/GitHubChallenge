package com.adsoft.githubchallenge.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
class UserDatabaseModel(
    @ColumnInfo(name = "user_id") @PrimaryKey val userId: Long,
    @ColumnInfo(name = "user_login") val userLogin: String,
    @ColumnInfo(name = "user_avatar_url") val userAvatarUrl: String,
    @ColumnInfo(name = "profile_url") val userProfileUrl: String
)