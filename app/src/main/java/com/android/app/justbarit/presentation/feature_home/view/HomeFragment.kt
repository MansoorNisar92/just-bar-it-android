package com.android.app.justbarit.presentation.feature_home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.FragmentHomeBinding
import com.android.app.justbarit.domain.model.Category
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.common.customviews.EventTodayItem
import com.android.app.justbarit.presentation.common.ext.bitmapFromVector
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.hideProgress
import com.android.app.justbarit.presentation.common.ext.showProgress
import com.android.app.justbarit.presentation.feature_home.adapter.CategoryAdapter
import com.android.app.justbarit.presentation.feature_home.adapter.categoryClick
import com.android.app.justbarit.presentation.feature_home.viewmodel.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private var currentEventIndex = 0

    private var eventsList = arrayListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.eventLocationsMapFragment.onCreate(savedInstanceState)
        binding.eventLocationsMapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategories()
        attachListeners()
        observe()

        viewModel.getListOfCategories()
        viewModel.getListOfEventsToday()
    }

    private fun initCategories() {
        categoryAdapter = CategoryAdapter(arrayListOf()).apply {
            categoryClick = {
                it.selected = true
                updateCategory(it)
            }
        }
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    private fun initTodayEvents(events: ArrayList<String>) {
        eventsList = events
        showEvent(currentEventIndex)
    }

    private fun observe() {
        viewModel.apply {
            lifecycleScope.launchWhenCreated {
                categories.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            categoryAdapter.setCategories(it.response as ArrayList<Category>)
                        }

                        is AppState.Failure<*> -> {
                            hideProgress()
                        }

                        else -> {}
                    }
                }
            }

            lifecycleScope.launchWhenCreated {
                eventsToday.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            initTodayEvents(it.response as ArrayList<String>)
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
            eventArrowLeftLayout.clickToAction {
                showPreviousEvent()
            }

            eventArrowRightLayout.clickToAction {
                showNextEvent()
            }
        }
    }

    private fun showEvent(index: Int, isNext: Boolean = true) {
        binding.addEventTodayLinearLayout.removeAllViews()
        val eventItem = EventTodayItem(requireContext())
        eventItem.addEvent(getEventAtIndex(index))
        binding.addEventTodayLinearLayout.addView(eventItem)
        eventItem.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                eventItem.viewTreeObserver.removeOnPreDrawListener(this)
                eventItem.translationX = if (isNext) eventItem.width.toFloat() else -eventItem.width.toFloat()
                eventItem.animate().translationX(0f).setDuration(700).start()
                return true
            }
        })
    }


    private fun showNextEvent() {
        currentEventIndex = (currentEventIndex + 1) % totalEvents()
        showEvent(currentEventIndex, isNext = true)
    }

    private fun showPreviousEvent() {
        currentEventIndex = (currentEventIndex - 1 + totalEvents()) % totalEvents()
        showEvent(currentEventIndex, isNext = false)
    }

    private fun totalEvents(): Int {
        return eventsList.size
    }

    private fun getEventAtIndex(index: Int): String {
        return eventsList[index]
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        val mMap = googleMap

        val currentLocation = LatLng(33.5651107, 73.0169135)

        val newCustomMarker: BitmapDescriptor = R.drawable.ic_pin_location.bitmapFromVector(requireContext())
        val markerOptions = MarkerOptions()
            .position(currentLocation)
            .draggable(true)
            .icon(newCustomMarker)
        // Add a marker in a specific location and move the camera
        val location = LatLng(33.5651107, 73.0169135) // Example coordinates (San Francisco)
        mMap.addMarker(markerOptions)
        //marker.showInfoWindow();
        val camera = CameraPosition.Builder()
            .target(currentLocation)
            .zoom(14f) // limit -> 21
            .bearing(0f) // 0 - 365º
            //.tilt(30)           // limit -> 90
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}