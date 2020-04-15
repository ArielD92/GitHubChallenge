package com.adsoft.githubchallenge.di

import com.adsoft.githubchallenge.api.GitHubApiService
import com.adsoft.githubchallenge.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun networkModule() = module {
    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        get<Retrofit>().create(GitHubApiService::class.java)
    }
}