package com.adsoft.githubchallenge.view.viewHolders

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.adsoft.githubchallenge.databinding.LikedUserItemRowBinding
import com.adsoft.githubchallenge.room.UserDatabaseModel
import com.adsoft.githubchallenge.viewModel.FavouriteUsersViewModel
import com.bumptech.glide.Glide


class FavouriteUsersViewHolder(private val viewBinding: LikedUserItemRowBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private val favouriteUsersViewModel = FavouriteUsersViewModel()

    fun bind(userDatabaseModel: UserDatabaseModel) {
        favouriteUsersViewModel.bind(userDatabaseModel)
        viewBinding.viewModel = favouriteUsersViewModel

        Glide.with(viewBinding.root)
            .load(userDatabaseModel.userAvatarUrl)
            .into(viewBinding.userAvatar)

        onItemClickListener(userDatabaseModel)
    }

    private fun onItemClickListener(userDatabaseModel: UserDatabaseModel) {
        viewBinding.userAvatar.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                this.data = Uri.parse(userDatabaseModel.userProfileUrl)
                itemView.context.startActivity(this)
            }
        }
    }
}