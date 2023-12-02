package com.android.app.justbarit.presentation.feature_search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.FragmentSearchBinding
import com.android.app.justbarit.domain.model.Bar
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.common.SharedViewModel
import com.android.app.justbarit.presentation.common.ext.hideProgress
import com.android.app.justbarit.presentation.common.ext.navigate
import com.android.app.justbarit.presentation.common.ext.showProgress
import com.android.app.justbarit.presentation.feature_search.adapter.BarAdapter
import com.android.app.justbarit.presentation.feature_search.adapter.barClick
import com.android.app.justbarit.presentation.feature_search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var barAdapter: BarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initBars()
        attachListeners()
        observe()

        viewModel.getListOfBars()
    }

    private fun initView() {
        binding.apply {
            includedTopBar.fancyANewAdventureTextView.visibility = View.GONE
            includedTopBar.includedGeneralSearchBar.locationIconImageView.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.filter_icon))
            includedTopBar.includedGeneralSearchBar.searchInputField.doOnTextChanged { text, _, _, _ ->
                barAdapter.filter(text.toString())
            }
        }
    }

    private fun initBars() {
        barAdapter = BarAdapter(arrayListOf()).apply {
            barClick = {
                sharedViewModel.setSelectedBar(it)
                navigate(R.id.calendarDetailsFragment)
            }
        }
        binding.barRecyclerView.adapter = barAdapter
    }


    private fun observe() {
        viewModel.apply {
            lifecycleScope.launchWhenCreated {
                bars.collect {
                    when (it) {
                        is AppState.Loading -> {
                            showProgress()
                        }

                        is AppState.Success<*> -> {
                            hideProgress()
                            barAdapter.setBars(it.response as ArrayList<Bar>)
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

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}