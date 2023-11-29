package com.android.app.justbarit.presentation.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ActivityDashboardBinding
import com.android.app.justbarit.presentation.base.JustBarItBaseActivity
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.OnMenuItemSelectedListener
import com.android.app.justbarit.presentation.dashboard.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardScreen : JustBarItBaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private var isNavigatingFromNav = false
    private var destinationChangeListener: NavController.OnDestinationChangedListener? = null
    private var currentSelectedItem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavGraph()
        binding.includedBottomNavigationLayout.bottomNavigation.addBottomMenuItems(
            viewModel.navigationItems(
                this
            )
        )
        binding.includedBottomNavigationLayout.bottomNavigation.onMenuItemSelectedListener =
            OnMenuItemSelectedListener { index, _, _ ->
                onBottomNavItemClicked(index)
            }

        destinationChangeListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                handleDestinationChanged(destination)
            }

        navController()?.addOnDestinationChangedListener(destinationChangeListener!!)
    }


    private fun setNavGraph() {
        navController()?.apply {
            graph = navInflater.inflate(R.navigation.nav_graph).apply {
                setStartDestination(R.id.homeFragment)
            }
        }
    }

    private fun navController(): NavController? {
        if (supportFragmentManager.findFragmentById(R.id.navHostFragmentView) != null){
            val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragmentView) as NavHostFragment
            return navHost.navController
        }
        return null
    }

    private fun onBottomNavItemClicked(item: Int) {
        when (item) {
            0 -> {
                navigate(R.id.homeFragment)
            }

            1 -> {
                navigate(R.id.calendarFragment)
            }

            2 -> {
                navigate(R.id.searchFragment)
            }

            3 -> {
                navigate(R.id.starFragment)
            }

            4 -> {
                navigate(R.id.profileFragment)
            }
        }
    }

    private fun navigate(@IdRes id: Int) {
        val navOptions = navOptions(id)
        navController()?.navigate(id, null, navOptions = navOptions)
    }

    private fun navOptions(destination: Int) = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in)
        .setExitAnim(R.anim.slide_out)
        .setPopEnterAnim(R.anim.slide_in)
        .setPopExitAnim(R.anim.slide_out)
        .setPopUpTo(destination, true)
        .build()


    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_graph)
        val navController = navHostFragment?.findNavController()

        if (navController?.popBackStack() != true) {
            super.onBackPressed()
        }
        binding.includedBottomNavigationLayout.bottomNavigation.reRenderBottomNavigation(
            currentSelectedItem
        )
    }

    private fun handleDestinationChanged(destination: NavDestination) {
        if (!isNavigatingFromNav) {
            isNavigatingFromNav = true
            when (destination.id) {
                R.id.homeFragment -> {
                    currentSelectedItem = 0
                }

                R.id.calendarFragment -> {
                    currentSelectedItem = 1
                }

                R.id.searchFragment -> {
                    currentSelectedItem = 2
                }

                R.id.starFragment -> {
                    currentSelectedItem = 3
                }

                R.id.profileFragment -> {
                    currentSelectedItem = 4
                }
            }
            isNavigatingFromNav = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navController()?.removeOnDestinationChangedListener(destinationChangeListener!!)

    }

    fun showBottomNavigation() {
        renderNavigation()
    }

    fun hideBottomNavigation() {
        hideNavigation()
    }

    private fun renderNavigation() {
        binding.includedBottomNavigationLayout.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideNavigation() {
        binding.includedBottomNavigationLayout.bottomNavigation.visibility = View.GONE
    }
}