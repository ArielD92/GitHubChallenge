package com.adsoft.githubchallenge.view.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.adsoft.githubchallenge.databinding.RepositoryItemRowBinding
import com.adsoft.githubchallenge.model.Repository
import com.adsoft.githubchallenge.viewModel.ReposViewModel
import com.bumptech.glide.Glide

class ReposViewHolder(private val viewBinding: RepositoryItemRowBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    private val reposViewModel = ReposViewModel()
    fun bind(repository: Repository) {
        reposViewModel.bind(repository)
        viewBinding.viewModel = reposViewModel

        Glide.with(viewBinding.root)
            .load(repository.repositoryOwner.ownerAvatarUrl)
            .into(viewBinding.reposIcon)
    }
}