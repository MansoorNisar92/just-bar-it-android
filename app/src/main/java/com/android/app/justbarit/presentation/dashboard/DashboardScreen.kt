package com.android.app.justbarit.presentation.dashboard

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
        binding.includedBottomNavigationLayout.bottomNavigation.setOnItemSelectedListener {
            onBottomNavItemClicked(it)
            true
        }
    }

    private fun onBottomNavItemClicked(item: MenuItem) {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val iconView = binding.includedBottomNavigationLayout.bottomNavigation.findViewById<View>(item.itemId)
        iconView.startAnimation(animation)
        when (item.itemId) {
            R.id.action_home -> {}
            R.id.action_calendar -> {}
            R.id.action_star -> {}
            R.id.action_profile -> {}
        }
    }
}