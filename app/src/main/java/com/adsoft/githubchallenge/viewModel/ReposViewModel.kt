package com.adsoft.githubchallenge.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.api.GitHubApiService
import com.adsoft.githubchallenge.model.Repos
import com.adsoft.githubchallenge.model.Repository
import com.adsoft.githubchallenge.utils.RetrieveDataFromRequest
import com.adsoft.githubchallenge.view.ReposAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class ReposViewModel : ViewModel(), RetrieveDataFromRequest<Repos>, KoinComponent {
    private lateinit var reposSubscription: Disposable

    private val gitHubApiService: GitHubApiService by inject()
    val repositoryName = MutableLiveData<String>()
    val repositoryLink = MutableLiveData<String>()
    val repositoryOwner = MutableLiveData<String>()
    val repositoryLanguage = MutableLiveData<String>()
    val recyclerAdapter: ReposAdapter = ReposAdapter()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val errorClickListener = View.OnClickListener {
        errorMessage.value = null
    }

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    init {
        loadingVisibility.value = View.GONE
    }

    fun bind(repository: Repository) {
        repositoryName.value = repository.repositoryName ?: "Brak danych"
        repositoryLink.value = repository.repositoryUrl ?: "Brak danych"
        repositoryOwner.value = repository.repositoryOwner.ownerLogin ?: "Brak danych"
        repositoryLanguage.value = repository.repositoryLanguage ?: "Brak danych"
    }

    fun loadSearchRepository(searchQuery: String) {
        reposSubscription = gitHubApiService.getSearchGitHubRepositories(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLoadDataStart() }
            .doOnTerminate { onRetrieveLoadDataFinish() }
            .subscribe(
                {result -> onRetrieveLoadDataSuccess(result)},
                {onRetrieveLoadDataError()}
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

    override fun onRetrieveLoadDataSuccess(results: Repos) {
        recyclerAdapter.updateRepositoryList(results.reposList)
    }

    override fun onCleared() {
        super.onCleared()
        if(::reposSubscription.isInitialized) reposSubscription.dispose()
    }

}
