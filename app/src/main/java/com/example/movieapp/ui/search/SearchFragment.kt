package com.example.movieapp.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.common.utils.NetworkResult
import com.example.movieapp.data.model.search.Results
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.adapter.search.SearchAdapter
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.customNavigate
import com.example.movieapp.viewmodel.FilmViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val filmViewModel: FilmViewModel by viewModels()
    private val searchProductAdapter: SearchAdapter by lazy { SearchAdapter() }
    private var isPopStackAction = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!isPopStackAction) {
            _binding = FragmentSearchBinding.inflate(inflater, container, false)
        } else {
            isPopStackAction = true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupAdapters()
        subscribeObservers()
        handleClickEvents()
        setupSearch()
    }


    private fun setupSearch() {
        binding.etSearch.doOnTextChanged { inputText, _, _, _ ->
            if (inputText != null) {
                if (inputText.isEmpty()) {

                } else {
                    if (inputText.toString() == binding.etSearch.text.toString()) {
                        isPopStackAction = false
                    }
                    val currentTextLength = inputText.length
                    if (currentTextLength >= 2) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (currentTextLength == inputText.length && !isPopStackAction) {
                                // TODO search
                                filmViewModel.getMovieSearch(inputText.toString())
                            }
                        }, 500)
                    }
                }
            }
        }
    }

    private fun initialize() {
        mainActivity = requireActivity() as MainActivity
    }

    private fun setupAdapters() {
        binding.apply {
            rvSearch.apply {
                adapter = searchProductAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun subscribeObservers() {
        filmViewModel.movieSearchResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Loading -> {
                    mainActivity.showLoading()
                }
                is NetworkResult.Error -> {
                    mainActivity.hideLoading()
                }
                is NetworkResult.Success -> {
                    mainActivity.hideLoading()
                    response?.data?.results.let { list ->
                        handleMovieSearchResponse(list)
                    }
                    filmViewModel.clearMovieSearchResponse()
                }
            }
        }
    }

    private fun handleMovieSearchResponse(searchList: ArrayList<Results>) {
        searchProductAdapter.differ.submitList(searchList)
    }

    private fun handleClickEvents() {
        searchProductAdapter.apply {
            setOnItemClickListener { currentItem ->
                currentItem.id?.let {
                    val bundle = Bundle().apply {
                        putInt(Constants.MOVIE_BUNDLE_KEY, it)
                    }
                    customNavigate(R.id.searchFragment_to_filmDetailFragment, bundle)
                } ?: run {
                    Toast.makeText(requireContext(), "Sorry, Unexpected Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}