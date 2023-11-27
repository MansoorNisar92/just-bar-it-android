package com.android.app.justbarit.presentation.feature_calendar.view

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.FragmentCalendarBinding
import com.android.app.justbarit.domain.model.CalendarItem
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.fallDownAnimation
import com.android.app.justbarit.presentation.common.ext.hideProgress
import com.android.app.justbarit.presentation.common.ext.navigate
import com.android.app.justbarit.presentation.common.ext.showProgress
import com.android.app.justbarit.presentation.feature_calendar.adapter.CalendarItemAdapter
import com.android.app.justbarit.presentation.feature_calendar.adapter.UpcomingEventAdapter
import com.android.app.justbarit.presentation.feature_calendar.adapter.calendarItemClick
import com.android.app.justbarit.presentation.feature_calendar.adapter.upComingEventDetails
import com.android.app.justbarit.presentation.feature_calendar.viewmodel.CalendarViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var calendarItemAdapter: CalendarItemAdapter
    private lateinit var upcomingEventAdapter: UpcomingEventAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarItems()
        initEvents()
        attachListeners()
        observe()
        viewModel.getListOfCalendarItems()
        viewModel.getListOfEvents()
    }

    private fun observe() {
        viewModel.apply {
            lifecycleScope.launchWhenCreated {
                calendarItems.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            calendarItemAdapter.setCalendarItems(it.response as ArrayList<CalendarItem>)
                        }

                        is AppState.Failure<*> -> {
                            hideProgress()
                        }

                        else -> {}
                    }
                }
            }

            lifecycleScope.launchWhenCreated {
                events.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            upcomingEventAdapter.setEventItems(it.response as ArrayList<EventDetails>)
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

            calendarArrowLeftLayout.clickToAction {
                val currentPosition = calendarItemAdapter.selectedItemPosition()

                if (currentPosition > 0) {
                    //val scroller = binding.calendarItemRecyclerView.smoothScroll(currentPosition - 1)
                    val smoothScroller =
                        object : LinearSmoothScroller(binding.calendarItemRecyclerView.context) {
                            override fun getVerticalSnapPreference(): Int {
                                return SNAP_TO_START
                            }

                            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                                return 0.5f // Adjust the scrolling speed as needed
                            }
                        }

                    smoothScroller.targetPosition = currentPosition - 1

                    // Update the item position after starting the smooth scroll
                    calendarItemAdapter.updateItemAtPosition(currentPosition - 1)

                    (binding.calendarItemRecyclerView.layoutManager as LinearLayoutManager).startSmoothScroll(
                        smoothScroller
                    )
                }
            }

            calendarArrowRightLayout.clickToAction {
                val lastPosition =
                    (binding.calendarItemRecyclerView.layoutManager as LinearLayoutManager).itemCount - 1
                if (calendarItemAdapter.selectedItemPosition() < lastPosition) {
                    calendarItemAdapter.updateItemAtPosition(calendarItemAdapter.selectedItemPosition() + 1)
                    val smoothScroller =
                        object : LinearSmoothScroller(binding.calendarItemRecyclerView.context) {
                            override fun getVerticalSnapPreference(): Int {
                                return LinearSmoothScroller.SNAP_TO_START
                            }

                            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                                return 0.5f // Adjust the scrolling speed as needed
                            }
                        }

                    smoothScroller.targetPosition = calendarItemAdapter.selectedItemPosition() + 1
                    (binding.calendarItemRecyclerView.layoutManager as LinearLayoutManager).startSmoothScroll(
                        smoothScroller
                    )
                }
            }

            allTextView.clickToAction {
                updateAllTextViewBackground(selected = true)
                updateGoingTextViewBackground()
                upcomingEventAdapter.setEventItems(viewModel.getAllEvents())
            }

            goingToTextView.clickToAction {
                updateGoingTextViewBackground(selected = true)
                updateAllTextViewBackground()
                upcomingEventAdapter.setEventItems(viewModel.getMyEvents())
            }
        }
    }

    private fun initCalendarItems() {
        calendarItemAdapter = CalendarItemAdapter(arrayListOf(), requireContext()).apply {
            calendarItemClick = { calendarItem ->
                calendarItem.selected = true
                updateCalendarItem(calendarItem)
            }
        }
        binding.calendarItemRecyclerView.adapter = calendarItemAdapter
    }

    private fun initEvents() {
        upcomingEventAdapter = UpcomingEventAdapter(arrayListOf(), requireContext()).apply {
            upComingEventDetails = { event ->
                //navigate(R.id.calendarDetailsFragment)
            }
        }
        binding.calendarRecyclerView.adapter = upcomingEventAdapter
    }

    private fun updateAllTextViewBackground(selected: Boolean = false) {
        binding.allTextView.apply {
            val textColor: Int
            val backGround: Int
            if (selected) {
                textColor = R.color.white
                backGround = R.drawable.tab_layout_all_selected_background
            } else {
                textColor = R.color.black
                backGround = R.drawable.tab_layout_all_background
            }
            setTextColor(ContextCompat.getColor(requireContext(), textColor))
            setBackgroundDrawable(AppCompatResources.getDrawable(requireContext(), backGround))
            fallDownAnimation()
        }
    }

    private fun updateGoingTextViewBackground(selected: Boolean = false) {
        binding.goingToTextView.apply {
            val textColor: Int
            val backGround: Int
            if (selected) {
                textColor = R.color.white
                backGround = R.drawable.tab_layout_going_selected_background
            } else {
                textColor = R.color.black
                backGround = R.drawable.tab_layout_going_background
            }
            setTextColor(ContextCompat.getColor(requireContext(), textColor))
            setBackgroundDrawable(AppCompatResources.getDrawable(requireContext(), backGround))
            fallDownAnimation()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}