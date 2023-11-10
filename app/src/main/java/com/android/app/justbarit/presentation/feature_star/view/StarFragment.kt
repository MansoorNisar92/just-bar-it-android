package com.android.app.justbarit.presentation.feature_star.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.app.justbarit.databinding.FragmentStarBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StarFragment : Fragment() {
    private lateinit var binding: FragmentStarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StarFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}