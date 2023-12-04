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
import com.android.app.justbarit.domain.model.Bar
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
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var homeEventAdapter: HomeEventAdapter
    private var googleMap: GoogleMap? = null
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
        //viewModel.getCoordinates()
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
        homeEventAdapter = HomeEventAdapter(arrayListOf())
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

            lifecycleScope.launchWhenCreated {
                bars.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            extractCoordinatesAndDrawOnTheMap(it.response as ArrayList<Pair<Double, Double>>)
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

    override fun onMapReady(mMap: GoogleMap) {
        googleMap = mMap
        googleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        extractCoordinatesAndDrawOnTheMap(viewModel.fetchCoordinates())
    }

    private fun extractCoordinatesAndDrawOnTheMap(coordinates: List<Pair<Double, Double>>) {
        val newCustomMarker: BitmapDescriptor =
            R.drawable.ic_pin_location.bitmapFromVector(requireContext())

        googleMap?.let { map ->
            if (coordinates.isNotEmpty()) {
                coordinates.forEach { (lat, lng) ->
                    val latLng = LatLng(lat, lng)
                    val markerOptions = MarkerOptions()
                        .position(latLng)
                        .draggable(false)
                        .icon(newCustomMarker)

                    map.addMarker(markerOptions)
                }
                val builder = LatLngBounds.Builder()
                coordinates.forEach { (lat, lng) ->
                    builder.include(LatLng(lat, lng))
                }
                val bounds = builder.build()
                val padding = 50
                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                map.animateCamera(cameraUpdate)
            }
        }
    }
}