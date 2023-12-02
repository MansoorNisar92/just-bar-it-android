package com.android.app.justbarit.presentation.feature_star.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.FragmentStarBinding
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.common.ext.hideProgress
import com.android.app.justbarit.presentation.common.ext.navigate
import com.android.app.justbarit.presentation.common.ext.showProgress
import com.android.app.justbarit.presentation.feature_calendar.adapter.HomeEventAdapter
import com.android.app.justbarit.presentation.feature_calendar.adapter.homeEventClick
import com.android.app.justbarit.presentation.feature_star.viewmodel.StarViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StarFragment : Fragment() {
    private lateinit var binding: FragmentStarBinding
    private val viewModel: StarViewModel by viewModels()
    private lateinit var favouriteEventAdapter: HomeEventAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavouritesEvents()
        observe()
        viewModel.getListOfFavouriteEvents()
    }

    private fun initFavouritesEvents() {
        favouriteEventAdapter = HomeEventAdapter(arrayListOf(), requireContext()).apply {
            homeEventClick = {
                //navigate(R.id.calendarDetailsFragment)
            }
        }
        binding.favouriteEventListRecyclerView.adapter = favouriteEventAdapter
    }

    private fun observe() {
        viewModel.apply {
            lifecycleScope.launchWhenCreated {
                favouriteEvents.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            favouriteEventAdapter.setEventItems(it.response as ArrayList<EventDetails>)
                        }

                        is AppState.Failure<*> -> {
                            hideProgress()
                        }

                        else -> {}
                    }
                }
            }
        }
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