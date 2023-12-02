package com.android.app.justbarit.presentation.splash.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ActivitySplashBinding
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.base.JustBarItBaseActivity
import com.android.app.justbarit.presentation.common.ext.navigate
import com.android.app.justbarit.presentation.dashboard.DashboardScreen
import com.android.app.justbarit.presentation.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : JustBarItBaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
        splashViewModel.fetchBars(this, R.raw.justbarit_android_data)
    }

    private fun observe() {
        splashViewModel.apply {
            lifecycleScope.launchWhenCreated {
                barsResponse.collect {
                    when (it) {
                        is AppState.Success<*> -> {
                            navigateToDashBoard()
                        }

                        is AppState.Failure<*> -> {
                            //
                        }

                        else -> {}
                    }
                }
            }

        }
    }
    private fun navigateToDashBoard() {
        navigate<DashboardScreen>()
        finish()
    }
}