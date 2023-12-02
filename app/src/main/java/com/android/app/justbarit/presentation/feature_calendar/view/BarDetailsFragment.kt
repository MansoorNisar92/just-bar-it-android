package com.android.app.justbarit.presentation.feature_calendar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.FragmentCalendarDetailsBinding
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.domain.model.Review
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.common.SharedViewModel
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.hideProgress
import com.android.app.justbarit.presentation.common.ext.loadImageWithImageId
import com.android.app.justbarit.presentation.common.ext.popBackStack
import com.android.app.justbarit.presentation.common.ext.showProgress
import com.android.app.justbarit.presentation.common.ext.showSnackBar
import com.android.app.justbarit.presentation.dashboard.DashboardScreen
import com.android.app.justbarit.presentation.feature_calendar.adapter.EventAdapter
import com.android.app.justbarit.presentation.feature_calendar.adapter.ReviewAdapter
import com.android.app.justbarit.presentation.feature_calendar.viewmodel.BarDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BarDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCalendarDetailsBinding
    private val viewModel: BarDetailsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var eventAdapter: EventAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private var givenRating: Boolean = false
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
        //binding.eventCoverImageView.loadImageFromAssets(R.drawable.bar_cover_dummy)
        populateBarDataIfPossible()
        initEvents()
        initReviews()
        attachListeners()
        observe()

        viewModel.getListOfEvents()
        viewModel.getListOfReviews()
    }

    private fun populateBarDataIfPossible() {
        if (sharedViewModel.getSelectedBar() != null) {
            sharedViewModel.getSelectedBar()?.apply {
                binding.eventCoverImageView.loadImageWithImageId(imageId)
                binding.barNameTextView.text = barName
                binding.barRatingTextView.text = barRating.toString()
                val review = if (reviewCount > 0) {
                    "(${reviewCount})"
                } else {
                    "(No Reviews)"
                }
                binding.barReviewCountTextView.text = review
                var distanceStr = "Bar Restaurant - {DIS} from city center"
                distanceStr = distanceStr.replace("{DIS}", distance.toString())
                binding.barDistanceTextView.text = distanceStr
                val des = StringBuilder("Meeting place for historic rebels, this brass-filled, lantern-lit pub ")
                des.append(viewModel.prepareBarDescription(this))
                binding.barDescTextView.text = des

            }
        }
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

            reviewLeftArrow.clickToAction {
                val firstVisibleItemPosition =
                    (binding.reviewRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (firstVisibleItemPosition > 0) {
                    binding.reviewRecyclerView.smoothScrollToPosition(firstVisibleItemPosition - 1)
                }
            }

            reviewRightArrow.clickToAction {
                val lastVisibleItemPosition =
                    (binding.reviewRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastVisibleItemPosition < binding.reviewRecyclerView.adapter?.itemCount ?: 0 - 1) {
                    binding.reviewRecyclerView.smoothScrollToPosition(lastVisibleItemPosition + 1)
                }
            }

            giveRatingImageView.clickToAction {
                if (givenRating.not()) {
                    givenRating = true
                    giveRatingImageView.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.star_filled
                        )
                    )
                    showSnackBar("Bar added a favourite")
                } else {

                    giveRatingImageView.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.star_icon_black
                        )
                    )
                    givenRating = false
                    showSnackBar("Bar removed from favourites")
                }
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
            BarDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}