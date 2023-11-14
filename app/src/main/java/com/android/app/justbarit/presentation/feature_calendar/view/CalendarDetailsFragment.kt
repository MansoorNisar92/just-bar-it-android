package com.android.app.justbarit.presentation.feature_calendar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.app.justbarit.databinding.FragmentCalendarDetailsBinding
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.domain.model.Review
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.hideProgress
import com.android.app.justbarit.presentation.common.ext.popBackStack
import com.android.app.justbarit.presentation.common.ext.showProgress
import com.android.app.justbarit.presentation.dashboard.DashboardScreen
import com.android.app.justbarit.presentation.feature_calendar.adapter.EventAdapter
import com.android.app.justbarit.presentation.feature_calendar.adapter.ReviewAdapter
import com.android.app.justbarit.presentation.feature_calendar.viewmodel.CalendarDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CalendarDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCalendarDetailsBinding
    private val viewModel: CalendarDetailsViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarDetailsBinding.inflate(inflater, container, false)
        hideBottomNavigationOnArrival()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvents()
        initReviews()
        attachListeners()
        observe()

        viewModel.getListOfEvents()
        viewModel.getListOfReviews()
    }

    private fun initEvents() {
        eventAdapter = EventAdapter(arrayListOf(), requireContext())
        binding.eventListRecyclerView.adapter = eventAdapter
    }

    private fun initReviews() {
        reviewAdapter = ReviewAdapter(arrayListOf(), requireContext())
        binding.reviewRecyclerView.adapter = reviewAdapter
    }

    private fun observe() {
        viewModel.apply {
            lifecycleScope.launchWhenCreated {
                events.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            eventAdapter.setEventItems(it.response as ArrayList<EventDetails>)
                        }

                        is AppState.Failure<*> -> {
                            hideProgress()
                        }

                        else -> {}
                    }
                }
            }

            lifecycleScope.launchWhenCreated {
                reviews.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            reviewAdapter.setReviews(it.response as ArrayList<Review>)
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

    private fun attachListeners() {
        binding.apply {
            navigateBackFromEventDetailsPage.clickToAction {
                popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigationOnWayBack()
    }

    override fun onPause() {
        super.onPause()
        showBottomNavigationOnWayBack()
    }

    private fun hideBottomNavigationOnArrival() {
        (activity as DashboardScreen).hideBottomNavigation()
    }

    private fun showBottomNavigationOnWayBack() {
        (activity as DashboardScreen).showBottomNavigation()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}