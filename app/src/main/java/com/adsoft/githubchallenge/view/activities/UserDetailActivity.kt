package com.adsoft.githubchallenge.view.activities

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.databinding.ActivityUserDetailBinding
import com.adsoft.githubchallenge.model.GitHubUser
import com.adsoft.githubchallenge.utils.Constants
import com.adsoft.githubchallenge.utils.Constants.INTENT_EXTRAS_USER_LOGIN
import com.adsoft.githubchallenge.utils.displayToast
import com.adsoft.githubchallenge.viewModel.LoadPublicRepositoryViewModel
import com.adsoft.githubchallenge.viewModel.UserDetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserDetailActivity : AppCompatActivity() {
    private lateinit var activityBinding: ActivityUserDetailBinding
    private val detailViewModel: UserDetailViewModel by viewModel()
    private val loadPublicRepositoryViewModel: LoadPublicRepositoryViewModel by viewModel()
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        activityBinding.publicReposRecycler.layoutManager =
            LinearLayoutManager(activityBinding.root.context, LinearLayoutManager.VERTICAL, false)

        intent?.getStringExtra(INTENT_EXTRAS_USER_LOGIN)?.let { username ->
            detailViewModel.loadDetailUserData(username)
            loadPublicRepositoryViewModel.loadUserPublicRepos(username)

            detailViewModel.errorMessage.observe(this, Observer { errorMessage ->
                if (errorMessage != null) showError(errorMessage, username) else hideError()
            })
        }

        getUserExtrasFromIntent()
        activityBinding.viewModel = detailViewModel
        activityBinding.repositoryViewModel = loadPublicRepositoryViewModel
    }

    private fun getUserExtrasFromIntent() {
        val bundle = intent?.extras
        bundle?.let { gitHubUser ->
            val userToSaveInDatabase =
                gitHubUser.getSerializable(Constants.INTENT_EXTRAS_USER_DATA) as GitHubUser
            likeButtonClickListener(userToSaveInDatabase)
        }
    }

    private fun likeButtonClickListener(gitHubUser: GitHubUser) {
        addToFavouriteButton.setOnClickListener {
            detailViewModel.insertLikeUserToDatabase(gitHubUser)
            addToFavouriteButton.isActivated = false
            displayToast(getString(R.string.add_to_favourite))
        }
    }

    private fun showError(@StringRes errorMessage: Int, username: String) {
        errorSnackbar =
            Snackbar.make(activityBinding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, detailViewModel.onErrorClickLister(username))
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
