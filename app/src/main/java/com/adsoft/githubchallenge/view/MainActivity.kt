package com.adsoft.githubchallenge.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.adsoft.githubchallenge.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(setupBottomNavigation())
        switchToFragment(LoggedUserFragment())
    }

    private fun setupBottomNavigation(): BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val loadFragment = when (item.itemId) {
                R.id.navigationLoggedUser -> LoggedUserFragment()
                R.id.navigationRepos -> ReposFragment()
                R.id.navigationGithubUsers -> UsersFragment()
                else -> LoggedUserFragment()
            }
            switchToFragment(loadFragment)
            true
        }

    private fun switchToFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit()
}
