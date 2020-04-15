package com.adsoft.githubchallenge.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adsoft.githubchallenge.databinding.LikedUserItemRowBinding
import com.adsoft.githubchallenge.room.UserDatabaseModel
import com.adsoft.githubchallenge.view.viewHolders.FavouriteUsersViewHolder

class FavouriteUsersAdapter : RecyclerView.Adapter<FavouriteUsersViewHolder>() {
    private lateinit var usersList: List<UserDatabaseModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteUsersViewHolder {
        return FavouriteUsersViewHolder(
            LikedUserItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (::usersList.isInitialized) usersList.size else 0
    }

    override fun onBindViewHolder(holder: FavouriteUsersViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    fun updateUsersList(usersList: List<UserDatabaseModel>) {
        this.usersList = usersList
        notifyDataSetChanged()
    }
}