package com.adsoft.githubchallenge.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adsoft.githubchallenge.api.GitHubApiService
import com.adsoft.githubchallenge.model.Repos
import com.adsoft.githubchallenge.model.Repository
import com.adsoft.githubchallenge.utils.RetrieveDataFromRequest
import com.adsoft.githubchallenge.view.adapters.PublicRepositoryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoadPublicRepositoryViewModel : ViewModel(), KoinComponent,
    RetrieveDataFromRequest<List<Repository>> {
    private lateinit var loadReposSubscription: Disposable
    private val gitHubApiService: GitHubApiService by inject()

    val repositoryLink = MutableLiveData<String>()
    val recyclerAdapter = PublicRepositoryAdapter()

    fun bind(repository: Repository) {
        repositoryLink.value = repository.repositoryUrl
    }

    fun loadUserPublicRepos(username: String) {
        loadReposSubscription = gitHubApiService.getUserPublicRepos(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLoadDataStart() }
            .doOnTerminate { onRetrieveLoadDataFinish() }
            .subscribe(
                { result -> onRetrieveLoadDataSuccess(result) },
                { onRetrieveLoadDataError() }
            )
    }

    override fun onRetrieveLoadDataStart() {}

    override fun onRetrieveLoadDataFinish() {}

    override fun onRetrieveLoadDataError() {}

    override fun onRetrieveLoadDataSuccess(results: List<Repository>) {
        recyclerAdapter.updateRepositoryList(results)
    }

    override fun onCleared() {
        super.onCleared()
        if (::loadReposSubscription.isInitialized) loadReposSubscription.dispose()
    }
}