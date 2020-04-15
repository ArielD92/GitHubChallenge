package com.adsoft.githubchallenge.view.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.databinding.ReposFragmentBinding
import com.adsoft.githubchallenge.utils.OnTextChangedListener
import com.adsoft.githubchallenge.viewModel.ReposViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.repos_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class ReposFragment : Fragment(), OnTextChangedListener {
    private lateinit var reposBinding: ReposFragmentBinding
    private val reposViewModel: ReposViewModel by viewModel()
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupFragmentBinding(inflater, container)
        reposBinding.reposListRecycler.layoutManager =
            LinearLayoutManager(reposBinding.root.context, LinearLayoutManager.VERTICAL, false)

        reposViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        reposBinding.viewModel = reposViewModel
        return reposBinding.root
    }

    override fun onStart() {
        super.onStart()
        searcherEditText.addTextChangedListener(this)
        checkSearchEditText()
    }

    private fun setupFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) {
        reposBinding = ReposFragmentBinding.inflate(inflater, container, false)
        reposBinding.lifecycleOwner = this
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar =
            Snackbar.make(reposBinding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, reposViewModel.errorClickListener)
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
                reposViewModel.loadSearchRepository(searcherEditText.text.toString())
            }
        } else if(searcherEditText.text.isEmpty()) {
            searchButton.isEnabled = false
        }
    }

    private fun shouldSearchButtonBeEnabled() =
        searcherEditText.text.isNotEmpty() && searcherEditText.text.length > 3
}
