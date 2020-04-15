package com.adsoft.githubchallenge.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.api.GitHubApiService
import com.adsoft.githubchallenge.model.GitHubUser
import com.adsoft.githubchallenge.model.Users
import com.adsoft.githubchallenge.utils.RetrieveDataFromRequest
import com.adsoft.githubchallenge.view.adapters.UsersAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class UsersViewModel : ViewModel(), RetrieveDataFromRequest<Users>, KoinComponent {
    private lateinit var usersSubscription: Disposable
    private val gitHubApiService: GitHubApiService by inject()

    val userLogin = MutableLiveData<String>()
    val recyclerAdapter = UsersAdapter()
    val errorMessage = MutableLiveData<Int>()

    val errorClickListener = View.OnClickListener {
        errorMessage.value = null
    }

    val loadingVisibility = MutableLiveData<Int>()

    init {
        loadingVisibility.value = View.GONE
    }

    fun bind(gitHubUser: GitHubUser) {
        userLogin.value = gitHubUser.gitHubUserName
    }

    fun loadSearchUsers(searchQuery: String) {
        usersSubscription = gitHubApiService.getSearchGitHubUsers(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLoadDataStart() }
            .doOnTerminate { onRetrieveLoadDataFinish() }
            .subscribe(
                { result -> onRetrieveLoadDataSuccess(result)},
                { onRetrieveLoadDataError()}
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

    override fun onRetrieveLoadDataSuccess(results: Users) {
        recyclerAdapter.updateUsersList(results.usersList)
    }

    override fun onCleared() {
        super.onCleared()
        if (::usersSubscription.isInitialized) usersSubscription.dispose()
    }
}
