package com.adsoft.githubchallenge.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.api.GitHubApiService
import com.adsoft.githubchallenge.model.GitHubUser
import com.adsoft.githubchallenge.model.GitHubUserData
import com.adsoft.githubchallenge.room.UserRepository
import com.adsoft.githubchallenge.utils.RetrieveDataFromRequest
import com.adsoft.githubchallenge.utils.formatDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val gitHubApiService: GitHubApiService,
    private val userRepository: UserRepository
) : ViewModel(),
    RetrieveDataFromRequest<GitHubUserData> {
    private lateinit var userDetailSubscription: Disposable

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val userLogin = MutableLiveData<String>()
    val userAccountUrl = MutableLiveData<String>()
    val userAccountType = MutableLiveData<String>()
    val userBio = MutableLiveData<String>()
    val userPublicReposNumber = MutableLiveData<String>()
    val userFollowers = MutableLiveData<String>()
    val userFollowing = MutableLiveData<String>()
    val userAccountCreated = MutableLiveData<String>()
    val userAccountLastUpdate = MutableLiveData<String>()

    fun onErrorClickLister(username: String): View.OnClickListener = View.OnClickListener {
        loadDetailUserData(username)
    }

    fun loadDetailUserData(username: String) {
        userDetailSubscription = gitHubApiService.getGitHubUserData(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLoadDataStart() }
            .doOnTerminate { onRetrieveLoadDataFinish() }
            .subscribe(
                { result -> onRetrieveLoadDataSuccess(result) },
                { onRetrieveLoadDataError() }
            )
    }

    fun insertLikeUserToDatabase(gitHubUser: GitHubUser) {
        viewModelScope.launch {
            userRepository.addLikedUser(gitHubUser, userAccountUrl.value!!)
        }
    }

    override fun onRetrieveLoadDataStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    override fun onRetrieveLoadDataFinish() {
        loadingVisibility.value = View.GONE
    }

    override fun onRetrieveLoadDataError() {
        errorMessage.value = R.string.load_error
    }

    override fun onRetrieveLoadDataSuccess(results: GitHubUserData) {
        bindRetrieveData(results)
    }

    private fun bindRetrieveData(results: GitHubUserData) {
        userLogin.value = results.gitHubUserLogin
        userAccountUrl.value = results.gitHubUserUrlToAccount
        userAccountType.value = results.gitHubUserType
        userBio.value = results.gitHubUserBio ?: "Brak danych do wyświetlenia"
        userPublicReposNumber.value = results.gitHubUserPublicReposNumber.toString()
        userFollowers.value = results.gitHubUserFollowers.toString()
        userFollowing.value = results.gitHubUserFollowing.toString()
        userAccountCreated.value = results.gitHubUserAccountCreatedDate.formatDate()
        userAccountLastUpdate.value = results.gitHubUserAccountLastUpdateDate.formatDate()
    }

    override fun onCleared() {
        super.onCleared()
        if (::userDetailSubscription.isInitialized) userDetailSubscription.dispose()
    }
}