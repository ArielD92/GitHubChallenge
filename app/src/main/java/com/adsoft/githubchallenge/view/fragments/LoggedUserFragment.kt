package com.adsoft.githubchallenge.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.databinding.LogedUserFragmentBinding
import com.adsoft.githubchallenge.view.activities.LoginActivity
import com.adsoft.githubchallenge.viewModel.LoggedUserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class LoggedUserFragment : Fragment() {
    private lateinit var fragmentBinding: LogedUserFragmentBinding
    private val loggedUserViewModel: LoggedUserViewModel by viewModel()
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupFragmentBinding(inflater, container)
        getIntentData()?.let { username ->
            loggedUserViewModel.loadLoggedUserData(username)
            loggedUserViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
                if (errorMessage != null) showError(errorMessage, username) else hideError()
            })
        }

        loggedUserViewModel.userAvatarUrl.observe(viewLifecycleOwner, Observer { avatarUrl ->
            if(avatarUrl != null) {
                loadUserAvatar(avatarUrl)
            }
        })

        fragmentBinding.viewModel = loggedUserViewModel
        return fragmentBinding.root
    }

    private fun getIntentData(): String? =
        activity?.intent?.getStringExtra(LoginActivity.LOGIN_INPUT_EXTRA_INTENT)

    private fun setupFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = LogedUserFragmentBinding.inflate(inflater, container, false)
        fragmentBinding.lifecycleOwner = this
    }

    private fun showError(@StringRes errorMessage: Int, username: String) {
        errorSnackbar =
            Snackbar.make(fragmentBinding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, loggedUserViewModel.onErrorClickLister(username))
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun loadUserAvatar(avatarUrl: String) {
        Glide.with(fragmentBinding.root)
            .load(avatarUrl)
            .into(fragmentBinding.avatarImageView)
    }
}
