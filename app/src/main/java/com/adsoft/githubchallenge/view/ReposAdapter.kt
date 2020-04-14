package com.adsoft.githubchallenge.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adsoft.githubchallenge.databinding.RepositoryItemRowBinding
import com.adsoft.githubchallenge.model.Repository

class ReposAdapter : RecyclerView.Adapter<ReposViewHolder>() {
    private lateinit var reposList: List<Repository>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            RepositoryItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if(::reposList.isInitialized) reposList.size else 0
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(reposList[position])
    }

    fun updateRepositoryList(reposList: List<Repository>) {
        this.reposList = reposList
        notifyDataSetChanged()
    }
}