package com.codingblocks.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codingblocks.weatherapp.R
import com.codingblocks.weatherapp.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager.beginTransaction().apply {
            add(R.id.container, homeFragment, getString(R.string.home_dashboard))
        }.commit()
        initListeners()
    }

    private fun initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
                        .commit()
                    activeFragment = homeFragment
                    true
                }

                else -> false
            }
        }
    }
}

