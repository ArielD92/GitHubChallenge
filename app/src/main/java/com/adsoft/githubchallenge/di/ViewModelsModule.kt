package com.adsoft.githubchallenge.di

import com.adsoft.githubchallenge.viewModel.LoggedUserViewModel
import com.adsoft.githubchallenge.viewModel.ReposViewModel
import com.adsoft.githubchallenge.viewModel.UsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelsModule() = module {
    viewModel { LoggedUserViewModel(get()) }
    viewModel { ReposViewModel() }
    viewModel { UsersViewModel() }
}