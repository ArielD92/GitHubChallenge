package com.adsoft.githubchallenge.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adsoft.githubchallenge.room.UserDatabaseModel
import com.adsoft.githubchallenge.room.UserRepository
import com.adsoft.githubchallenge.view.adapters.FavouriteUsersAdapter
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FavouriteUsersViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val likedUsersList = MutableLiveData<List<UserDatabaseModel>>()
    val recyclerAdapter = FavouriteUsersAdapter()
    val userLogin = MutableLiveData<String>()

    fun getLikedUsers(): LiveData<List<UserDatabaseModel>> {
        viewModelScope.launch {
            likedUsersList.postValue(userRepository.getLikedUsers())
        }
        return likedUsersList
    }

    fun bind(userDatabaseModel: UserDatabaseModel) {
        userLogin.value = userDatabaseModel.userLogin
    }
}