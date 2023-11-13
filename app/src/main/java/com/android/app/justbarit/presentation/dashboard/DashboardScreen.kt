package com.android.app.justbarit.presentation.dashboard

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ActivityDashboardBinding
import com.android.app.justbarit.presentation.base.JustBarItBaseActivity
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.scaleUpAnimation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardScreen : JustBarItBaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private var isNavigatingFromNav = false
    private var destinationChangeListener: NavController.OnDestinationChangedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavGraph()
        attachListeners()
        binding.includedBottomNavigationLayout.bottomNavigation.apply {
            setOnItemSelectedListener {
                onBottomNavItemClicked(it)
                true
            }
        }

        destinationChangeListener = NavController.OnDestinationChangedListener { _, destination, _ ->
            handleDestinationChanged(destination)
        }

        navController().addOnDestinationChangedListener(destinationChangeListener!!)
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
        val navOptions = navOptions(id)
        navController().navigate(id, null, navOptions = navOptions)
    }

    private fun navOptions(destination: Int) = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in)
        .setExitAnim(R.anim.slide_out)
        .setPopEnterAnim(R.anim.slide_in)
        .setPopExitAnim(R.anim.slide_out)
        .setPopUpTo(destination, true)
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

    override fun onBackPressed() {
        // Assuming you have a NavHostFragment in your layout with ID nav_host_fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_graph)
        val navController = navHostFragment?.findNavController()

        val entryCountBefore = supportFragmentManager.backStackEntryCount
        val popped = navController?.popBackStack()
        val entryCountAfter = supportFragmentManager.backStackEntryCount

        Log.d("BackStack", "BackStack Entry Count Before: $entryCountBefore")
        Log.d("BackStack", "BackStack Popped: $popped")
        Log.d("BackStack", "BackStack Entry Count After: $entryCountAfter")


        // Handle back button press using NavController
        if (navController?.popBackStack() != true) {
            super.onBackPressed()
        }
    }
    private fun handleDestinationChanged(destination: NavDestination) {
        if (!isNavigatingFromNav) {
            isNavigatingFromNav = true
            when (destination.id) {
                R.id.homeFragment -> {
                    if (binding?.includedBottomNavigationLayout?.bottomNavigation?.selectedItemId != R.id.action_home) {
                        binding?.includedBottomNavigationLayout?.bottomNavigation?.selectedItemId = R.id.action_home
                    }
                }
                R.id.calendarFragment -> {
                    if (binding?.includedBottomNavigationLayout?.bottomNavigation?.selectedItemId != R.id.action_calendar) {
                        binding?.includedBottomNavigationLayout?.bottomNavigation?.selectedItemId = R.id.action_calendar
                    }
                }
                R.id.starFragment -> {
                    if (binding?.includedBottomNavigationLayout?.bottomNavigation?.selectedItemId != R.id.action_star) {
                        binding?.includedBottomNavigationLayout?.bottomNavigation?.selectedItemId = R.id.action_star
                    }
                }
                R.id.profileFragment -> {
                    if (binding?.includedBottomNavigationLayout?.bottomNavigation?.selectedItemId != R.id.action_profile) {
                        binding?.includedBottomNavigationLayout?.bottomNavigation?.selectedItemId = R.id.action_profile
                    }
                }
            }
            isNavigatingFromNav = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navController().removeOnDestinationChangedListener(destinationChangeListener!!)

    }
}