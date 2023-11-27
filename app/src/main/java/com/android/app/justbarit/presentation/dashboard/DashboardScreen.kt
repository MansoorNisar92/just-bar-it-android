package com.android.app.justbarit.presentation.dashboard

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ActivityDashboardBinding
import com.android.app.justbarit.presentation.base.JustBarItBaseActivity
import com.android.app.justbarit.presentation.common.ext.scaleUpAnimation
import com.skydoves.androidbottombar.BottomMenuItem
import com.skydoves.androidbottombar.forms.iconForm
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

        val list = mutableListOf<BottomMenuItem>()

        // we can create the form using kotlin dsl.
        val iconHome = iconForm(this) {
            setIcon(R.drawable.home_icon_selected)
            setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
            setIconSize(24) // sets the size of the icon.
        }

        val iconCalendar = iconForm(this) {
            setIcon(R.drawable.calendar_icon_selected)
            setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
            setIconSize(24) // sets the size of the icon.
        }

        val iconSearch = iconForm(this) {
            setIcon(R.drawable.search_icon)
            setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
            setIconSize(24) // sets the size of the icon.
        }

        val iconStar = iconForm(this) {
            setIcon(R.drawable.star_icon_selected)
            setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
            setIconSize(24) // sets the size of the icon.
        }

        val iconProfile = iconForm(this) {
            setIcon(R.drawable.profile_icon_selected)
            setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
            setIconSize(24) // sets the size of the icon.
        }



        val home = BottomMenuItem(this)
            .setIconForm(iconHome)
            .setIcon(R.drawable.home_icon)
            .setIconActiveColor(ContextCompat.getColor(this,R.color.nav_bar_color))
            .build()

        val calendar = BottomMenuItem(this)
            .setIconForm(iconCalendar)
            .setIcon(R.drawable.calendar_icon)
            .setIconActiveColor(ContextCompat.getColor(this,R.color.nav_bar_color))
            .build()

        val search = BottomMenuItem(this)
            .setIconForm(iconSearch)
            .setIcon(R.drawable.search_icon_bottom_nav)
            .setIconActiveColor(ContextCompat.getColor(this,R.color.nav_bar_color))
            .build()

        val star = BottomMenuItem(this)
            .setIconForm(iconStar)
            .setIcon(R.drawable.star_icon)
            .setIconActiveColor(ContextCompat.getColor(this,R.color.nav_bar_color))
            .build()

        val profile = BottomMenuItem(this)
            .setIconForm(iconProfile)
            .setIcon(R.drawable.profile_icon)
            .setIconActiveColor(ContextCompat.getColor(this,R.color.nav_bar_color))
            .build()
        list.add(home)
        list.add(calendar)
        list.add(search)
        list.add(star)
        list.add(profile)
        binding.testBottomBar.addBottomMenuItems(list)
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
        //updateSearchIconCircle()
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
            R.id.action_search -> {
                navigate(R.id.searchFragment)
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


    override fun onBackPressed() {
        // Assuming you have a NavHostFragment in your layout with ID nav_host_fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_graph)
        val navController = navHostFragment?.findNavController()

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

    fun showBottomNavigation(){
        renderNavigation()
    }

    fun hideBottomNavigation(){
        hideNavigation()
    }
    private fun renderNavigation(){
        binding.includedBottomNavigationLayout.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideNavigation(){
        binding.includedBottomNavigationLayout.bottomNavigation.visibility = View.GONE
    }
}