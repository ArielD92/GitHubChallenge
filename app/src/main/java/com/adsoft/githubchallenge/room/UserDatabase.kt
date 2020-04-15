package com.adsoft.githubchallenge.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserDatabaseModel::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "USER_DATABASE"

        @Volatile
        private var instance: UserDatabase? = null

        @Volatile
        private var context: Context? = null

        fun getDatabaseInstance(context: Context): UserDatabase {
            Companion.context = context
            return instance
                ?: synchronized(this) {
                    buildDatabase(context)
                }
                    .also { instance = it }
        }

        private fun buildDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(context, UserDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}