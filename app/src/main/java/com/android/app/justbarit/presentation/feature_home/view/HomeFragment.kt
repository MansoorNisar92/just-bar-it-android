package com.android.app.justbarit.presentation.feature_home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.FragmentHomeBinding
import com.android.app.justbarit.domain.model.Category
import com.android.app.justbarit.domain.model.Event
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.common.ext.bitmapFromVector
import com.android.app.justbarit.presentation.common.ext.hideProgress
import com.android.app.justbarit.presentation.common.ext.navigate
import com.android.app.justbarit.presentation.common.ext.showProgress
import com.android.app.justbarit.presentation.feature_calendar.adapter.HomeEventAdapter
import com.android.app.justbarit.presentation.feature_calendar.adapter.homeEventClick
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
    private lateinit var homeEventAdapter: HomeEventAdapter
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
        initView()
        attachListeners()
        observe()

        viewModel.getListOfCategories()
        viewModel.getListOfEventsToday()
    }

    private fun initView() {
        initCategories()
        initHomeEvents()
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

    private fun initHomeEvents() {
        homeEventAdapter = HomeEventAdapter(arrayListOf(), requireContext()).apply {
            homeEventClick = {
                navigate(R.id.calendarDetailsFragment)
            }
        }
        binding.homeEventListRecyclerView.adapter = homeEventAdapter
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
                            homeEventAdapter.setEventItems(it.response as ArrayList<EventDetails>)
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
            //
        }
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

        val newCustomMarker: BitmapDescriptor =
            R.drawable.ic_pin_location.bitmapFromVector(requireContext())
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