package com.android.app.justbarit.presentation.dashboard

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ActivityDashboardBinding
import com.android.app.justbarit.presentation.base.JustBarItBaseActivity
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.scaleUpAnimation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardScreen : JustBarItBaseActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavGraph()
        attachListeners()
        binding.includedBottomNavigationLayout.bottomNavigation.setOnItemSelectedListener {
            onBottomNavItemClicked(it)
            true
        }
    }

    private fun attachListeners() {
        binding.apply {
            searchButton.clickToAction {
                adjustViewAppearanceBeforeNavigateSearch()
            }
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
        val iconView =
            binding.includedBottomNavigationLayout.bottomNavigation.findViewById<View>(item.itemId)
        iconView.scaleUpAnimation()
        updateSearchIconCircle()
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

    private fun navigate(@IdRes id: Int) {
        val navOptions = navOptions()
        navController().navigate(id, null, navOptions = navOptions)
    }

    private fun navOptions() = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in)
        .setExitAnim(R.anim.slide_out)
        .setPopEnterAnim(R.anim.slide_in)
        .setPopExitAnim(R.anim.slide_out)
        .build()

    private fun adjustViewAppearanceBeforeNavigateSearch() {
        binding.apply {
            includedBottomNavigationLayout.bottomNavigation.menu.findItem(R.id.action_fake).isChecked =
                true
            searchIconCardView.scaleUpAnimation()
            updateSearchIconCircle(
                R.drawable.bottom_nav_search_icon_background_selected,
                R.drawable.search_icon_white
            )
        }
        navigate(R.id.searchFragment)
    }

    private fun updateSearchIconCircle(
        background: Int = R.drawable.bottom_nav_search_icon_white_background,
        icon: Int = R.drawable.search_icon
    ) {
        binding.searchIconCardView.setBackgroundResource(background)
        binding.searchButton.setImageDrawable(AppCompatResources.getDrawable(this, icon))
    }
}