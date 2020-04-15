package com.adsoft.githubchallenge.main

import android.app.Application
import com.adsoft.githubchallenge.di.databaseModule
import com.adsoft.githubchallenge.di.networkModule
import com.adsoft.githubchallenge.di.repositoryModule
import com.adsoft.githubchallenge.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class GitHubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
        initializeTimber()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@GitHubApplication)
            modules(listOf(
                networkModule(),
                viewModelsModule(),
                databaseModule(),
                repositoryModule()
            ))
        }
    }

    private fun initializeTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return super.createStackElementTag(element) + ": ${element.lineNumber}"
            }
        })
    }
}