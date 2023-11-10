package com.android.app.justbarit.presentation.dashboard

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ActivityDashboardBinding
import com.android.app.justbarit.presentation.base.JustBarItBaseActvity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardScreen : JustBarItBaseActvity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavGraph()
        binding.includedBottomNavigationLayout.bottomNavigation.setOnItemSelectedListener {
            onBottomNavItemClicked(it)
            true
        }
    }

    private fun setNavGraph() {
        navController().apply {
            graph = navInflater.inflate(R.navigation.nav_graph).apply {
                setStartDestination(R.id.homeFragment)
            }
        }
    }

    private fun navController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentView) as NavHostFragment
        return navHost.navController
    }

    private fun onBottomNavItemClicked(item: MenuItem) {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val iconView =
            binding.includedBottomNavigationLayout.bottomNavigation.findViewById<View>(item.itemId)
        iconView.startAnimation(animation)
        when (item.itemId) {
            R.id.action_home -> {
                navigate(R.id.homeFragment)
            }
            R.id.action_calendar -> {
                navigate(R.id.calendarFragment)
            }
            R.id.action_star -> {
                navigate(R.id.starFragment)
            }
            R.id.action_profile -> {
                navigate(R.id.profileFragment)
            }
        }
    }

    private fun navigate(@IdRes id: Int){
        val navOptions = navOptions()
        navController().navigate(id, null, navOptions = navOptions)
    }

    private fun navOptions() = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in)
        .setExitAnim(R.anim.slide_out)
        .setPopEnterAnim(R.anim.slide_in)
        .setPopExitAnim(R.anim.slide_out)
        .build()
}