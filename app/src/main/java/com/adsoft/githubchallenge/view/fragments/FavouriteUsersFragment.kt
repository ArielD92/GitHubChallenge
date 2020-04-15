package com.adsoft.githubchallenge.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.adsoft.githubchallenge.databinding.FavouriteUsersFragmentBinding
import com.adsoft.githubchallenge.viewModel.FavouriteUsersViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FavouriteUsersFragment : Fragment() {
    private lateinit var fragmentBinding: FavouriteUsersFragmentBinding
    private val favouriteUsersViewModel: FavouriteUsersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FavouriteUsersFragmentBinding.inflate(inflater, container, false)
        fragmentBinding.lifecycleOwner = this

        fragmentBinding.likedUsersRecycler.layoutManager =
            GridLayoutManager(fragmentBinding.root.context, 2)

        fragmentBinding.viewModel = favouriteUsersViewModel
        return fragmentBinding.root
    }

    override fun onStart() {
        super.onStart()
        favouriteUsersViewModel.getLikedUsers().observe(viewLifecycleOwner, Observer { usersList ->
            usersList?.let {list ->
                favouriteUsersViewModel.recyclerAdapter.updateUsersList(list)
            }
        })
    }
}
