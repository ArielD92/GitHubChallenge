package com.adsoft.githubchallenge.view.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.adsoft.githubchallenge.databinding.PublicRepositoryItemRowBinding
import com.adsoft.githubchallenge.model.Repository
import com.adsoft.githubchallenge.viewModel.LoadPublicRepositoryViewModel

class PublicRepositoryViewHolder(private val viewBinding: PublicRepositoryItemRowBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    private val loadPublicRepositoryViewModel = LoadPublicRepositoryViewModel()

    fun bind(repository: Repository) {
        loadPublicRepositoryViewModel.bind(repository)
        viewBinding.viewModel = loadPublicRepositoryViewModel
    }
}