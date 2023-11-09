package com.android.app.justbarit.presentation.splash

import android.os.Bundle
import com.android.app.justbarit.databinding.ActivitySplashBinding
import com.android.app.justbarit.presentation.base.JustBarItBaseActvity
import com.android.app.justbarit.presentation.common.ext.navigate
import com.android.app.justbarit.presentation.dashboard.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreen : JustBarItBaseActvity() {
    private lateinit var binding: ActivitySplashBinding
    private val delay: Long = 8000 // 8 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            delay(delay)
            navigate<DashboardScreen>()
            finish()
        }
    }
}