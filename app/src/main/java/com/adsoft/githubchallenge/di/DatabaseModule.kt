package com.adsoft.githubchallenge.di

import com.adsoft.githubchallenge.room.UserDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun databaseModule() = module {
    single {
        UserDatabase.getDatabaseInstance(androidContext())
    }

    single {
        get<UserDatabase>().userDao()
    }
}