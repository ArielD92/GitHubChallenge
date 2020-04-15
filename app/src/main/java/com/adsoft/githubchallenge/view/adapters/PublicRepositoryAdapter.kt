package com.adsoft.githubchallenge.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adsoft.githubchallenge.databinding.PublicRepositoryItemRowBinding
import com.adsoft.githubchallenge.model.Repository
import com.adsoft.githubchallenge.view.viewHolders.PublicRepositoryViewHolder

class PublicRepositoryAdapter : RecyclerView.Adapter<PublicRepositoryViewHolder>() {
    private lateinit var reposList: List<Repository>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicRepositoryViewHolder {
        return PublicRepositoryViewHolder(
            PublicRepositoryItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if(::reposList.isInitialized) reposList.size else 0
    }

    override fun onBindViewHolder(holder: PublicRepositoryViewHolder, position: Int) {
        holder.bind(reposList[position])
    }

    fun updateRepositoryList(reposList: List<Repository>) {
        this.reposList = reposList
        notifyDataSetChanged()
    }
}