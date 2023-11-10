package com.android.app.justbarit.presentation.feature_home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.app.justbarit.databinding.FragmentHomeBinding
import com.android.app.justbarit.domain.model.Category
import com.android.app.justbarit.presentation.AppState
import com.android.app.justbarit.presentation.common.ext.hideProgress
import com.android.app.justbarit.presentation.common.ext.showProgress
import com.android.app.justbarit.presentation.feature_home.adapter.CategoryAdapter
import com.android.app.justbarit.presentation.feature_home.adapter.categoryClick
import com.android.app.justbarit.presentation.feature_home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategories()
        attachListeners()
        observe()

        viewModel.getListOfCategories()
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
        }
    }

    private fun attachListeners() {
        //
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}