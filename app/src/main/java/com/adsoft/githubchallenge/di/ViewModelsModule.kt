package com.adsoft.githubchallenge.di

import com.adsoft.githubchallenge.viewModel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelsModule() = module {
    viewModel { LoggedUserViewModel(get()) }
    viewModel { ReposViewModel() }
    viewModel { UsersViewModel() }
    viewModel { UserDetailViewModel(get(), get()) }
    viewModel { LoadPublicRepositoryViewModel() }
    viewModel { FavouriteUsersViewModel() }
}