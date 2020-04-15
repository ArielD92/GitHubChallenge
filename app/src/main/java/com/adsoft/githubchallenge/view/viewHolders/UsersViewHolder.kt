package com.adsoft.githubchallenge.view.viewHolders

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.adsoft.githubchallenge.databinding.UsersItemRowBinding
import com.adsoft.githubchallenge.model.GitHubUser
import com.adsoft.githubchallenge.utils.Constants.INTENT_EXTRAS_USER_DATA
import com.adsoft.githubchallenge.utils.Constants.INTENT_EXTRAS_USER_LOGIN
import com.adsoft.githubchallenge.view.activities.UserDetailActivity
import com.adsoft.githubchallenge.viewModel.UsersViewModel
import com.bumptech.glide.Glide

class UsersViewHolder(private val viewBinding: UsersItemRowBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private val usersViewModel = UsersViewModel()

    fun bind(gitHubUser: GitHubUser) {
        usersViewModel.bind(gitHubUser)
        viewBinding.viewModel = usersViewModel

        Glide.with(viewBinding.root)
            .load(gitHubUser.gitHubUserAvatarUrl)
            .into(viewBinding.userAvatar)

        onItemClickListener(gitHubUser)
    }

    private fun onItemClickListener(gitHubUser: GitHubUser) {
        viewBinding.userAvatar.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(INTENT_EXTRAS_USER_DATA, gitHubUser)
            Intent(itemView.context, UserDetailActivity::class.java).apply {
                putExtra(INTENT_EXTRAS_USER_LOGIN, gitHubUser.gitHubUserName)
                putExtras(bundle)
                itemView.context.startActivity(this)
            }
        }
    }
}