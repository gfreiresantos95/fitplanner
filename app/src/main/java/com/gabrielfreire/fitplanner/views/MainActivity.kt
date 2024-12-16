package com.gabrielfreire.fitplanner.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.databinding.ActivityMainBinding
import com.gabrielfreire.fitplanner.views.fragments.HomeFragment
import com.gabrielfreire.fitplanner.views.fragments.MoreOptionsFragment
import com.gabrielfreire.fitplanner.views.fragments.NotificationsFragment
import com.gabrielfreire.fitplanner.views.fragments.WorkoutsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initViewsAndSetListeners()
    }

    private fun initViewsAndSetListeners() {
        replaceFragment(HomeFragment())

        with(mainBinding) {
            mtMainToolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_notifications -> {
                        replaceFragment(NotificationsFragment())
                        true
                    }

                    else -> false
                }
            }

            bnvMainNavigation.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_home -> {
                        replaceFragment(HomeFragment())
                        true
                    }

                    R.id.menu_workouts -> {
                        replaceFragment(WorkoutsFragment())
                        true
                    }

                    R.id.menu_more_options -> {
                        replaceFragment(MoreOptionsFragment())
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(mainBinding.flMainContentContainer.id, fragment)
        fragmentTransaction.commit()
    }
}