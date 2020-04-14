package com.adsoft.githubchallenge.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.api.GitHubApiService
import com.adsoft.githubchallenge.model.LoggedUserData
import com.adsoft.githubchallenge.utils.RetrieveDataFromRequest
import com.adsoft.githubchallenge.utils.formatDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext

class LoggedUserViewModel(private val gitHubApiService: GitHubApiService) : ViewModel(),
    RetrieveDataFromRequest<LoggedUserData> {
    private lateinit var loggedUserSubscription: Disposable

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val userLogin = MutableLiveData<String>()
    val userAvatarUrl = MutableLiveData<String>()
    val userAccountUrl = MutableLiveData<String>()
    val userAccountType = MutableLiveData<String>()
    val userBio = MutableLiveData<String>()
    val userPublicReposNumber = MutableLiveData<String>()
    val userFollowers = MutableLiveData<String>()
    val userFollowing = MutableLiveData<String>()
    val userAccountCreated = MutableLiveData<String>()
    val userAccountLastUpdate = MutableLiveData<String>()

    fun onErrorClickLister(username: String): View.OnClickListener = View.OnClickListener {
        loadLoggedUserData(username)
    }

    fun loadLoggedUserData(username: String) {
        loggedUserSubscription = gitHubApiService.getLoggedUserData(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLoadDataStart() }
            .doOnTerminate { onRetrieveLoadDataFinish() }
            .subscribe(
                { result -> onRetrieveLoadDataSuccess(result) },
                { onRetrieveLoadDataError() }
            )
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

    override fun onRetrieveLoadDataSuccess(results: LoggedUserData) {
        bindRetrieveData(results)
    }

    private fun bindRetrieveData(results: LoggedUserData) {
        userLogin.value = results.gitHubUserLogin
        userAvatarUrl.value = results.gitHubUserAvatarUrl
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
        if(::loggedUserSubscription.isInitialized) loggedUserSubscription.dispose()
    }
}