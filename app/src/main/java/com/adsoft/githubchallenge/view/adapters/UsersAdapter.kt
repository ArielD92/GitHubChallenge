package com.adsoft.githubchallenge.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adsoft.githubchallenge.databinding.UsersItemRowBinding
import com.adsoft.githubchallenge.model.GitHubUser
import com.adsoft.githubchallenge.view.viewHolders.UsersViewHolder

class UsersAdapter : RecyclerView.Adapter<UsersViewHolder>() {
    private lateinit var usersList: List<GitHubUser>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            UsersItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (::usersList.isInitialized) usersList.size else 0
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    fun updateUsersList(usersList: List<GitHubUser>) {
        this.usersList = usersList
        notifyDataSetChanged()
    }
}