package com.adsoft.githubchallenge.view.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.databinding.UsersFragmentBinding
import com.adsoft.githubchallenge.utils.OnTextChangedListener
import com.adsoft.githubchallenge.viewModel.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.users_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class UsersFragment : Fragment(), OnTextChangedListener {
    private lateinit var usersBinding: UsersFragmentBinding
    private val usersViewModel: UsersViewModel by viewModel()
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupFragmentBinding(inflater, container)
        usersBinding.usersListRecycler.layoutManager =
            GridLayoutManager(usersBinding.root.context, 2)

        usersViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        usersBinding.viewModel = usersViewModel
        return usersBinding.root
    }

    override fun onStart() {
        super.onStart()
        searcherEditText.addTextChangedListener(this)
        checkSearchEditText()
    }

    private fun setupFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) {
        usersBinding = UsersFragmentBinding.inflate(inflater, container, false)
        usersBinding.lifecycleOwner = this
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar =
            Snackbar.make(usersBinding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, usersViewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    override fun onTextChangedListener(s: Editable?) {
        checkSearchEditText()
    }

    private fun checkSearchEditText() {
        if (shouldSearchButtonBeEnabled()) {
            searchButton.isEnabled = true
            searchButton.setOnClickListener {
                usersViewModel.loadSearchUsers(searcherEditText.text.toString())
            }
        } else if(searcherEditText.text.isEmpty()) {
            searchButton.isEnabled = false
        }
    }

    private fun shouldSearchButtonBeEnabled() =
        searcherEditText.text.isNotEmpty() && searcherEditText.text.length > 3
}
