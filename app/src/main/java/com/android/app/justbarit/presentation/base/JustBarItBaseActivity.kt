package com.android.app.justbarit.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.app.justbarit.presentation.common.JustBarItProgress

abstract class JustBarItBaseActivity : AppCompatActivity() {

    lateinit var justBarItProgress: JustBarItProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        justBarItProgress = JustBarItProgress(this)
    }
}