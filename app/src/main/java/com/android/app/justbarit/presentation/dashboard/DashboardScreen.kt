package com.android.app.justbarit.presentation.dashboard

import android.os.Bundle
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

    }
}