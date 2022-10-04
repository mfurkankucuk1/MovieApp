package com.example.movieapp.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.movieapp.R
import com.example.movieapp.common.utils.NetworkResult
import com.example.movieapp.data.model.upcoming.UpcomingResults
import com.example.movieapp.databinding.FragmentDiscoverBinding
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.adapter.discover.SliderAdapter
import com.example.movieapp.utils.Constants.MOVIE_BUNDLE_KEY
import com.example.movieapp.utils.SliderHelper
import com.example.movieapp.utils.customNavigate
import com.example.movieapp.viewmodel.FilmViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding: FragmentDiscoverBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val filmViewModel: FilmViewModel by viewModels()
    private val sliderAdapter: SliderAdapter by lazy { SliderAdapter() }
    lateinit var sliderHelper: SliderHelper
    private var isPopStackAction = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!isPopStackAction) {
            _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        } else {
            isPopStackAction = true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isPopStackAction) {
            initialize()
            setupAdapters()
            subscribeObservers()
        }
        handleClickEvents()
    }

    private fun initialize() {
        mainActivity = requireActivity() as MainActivity
        if (!isPopStackAction) {
            filmViewModel.getUpcomingFilms()
            binding.apply {
                sliderHelper = SliderHelper(requireContext(), llDotLayout, viewPagerSlider)
            }
        }

    }

    private fun setupAdapters() {
        binding.apply {
            viewPagerSlider.apply {
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.apply {
                    addTransformer(MarginPageTransformer(20))
                    addTransformer { page, position ->
                        val r = 1 - kotlin.math.abs(position)
                        page.scaleY = 0.85f + r * 0.15f
                    }
                }
                setPageTransformer(compositePageTransformer)
                adapter = sliderAdapter
            }
        }
    }

    private fun setupAutoSlider() {
        lifecycleScope.launch {
            sliderHelper.startSlider(
                3000
            )
        }
    }

    private fun subscribeObservers() {
        filmViewModel.upcomingFilmResponse.observe(viewLifecycleOwner) { response ->
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
                        setupSlider(list)
                    }
                    filmViewModel.clearUpcomingFilmResponse()
                }
            }
        }
    }

    private fun setupSlider(sliderList: ArrayList<UpcomingResults>) {
        if (sliderList.isNotEmpty()) {
            sliderHelper.setOnSubmitListener { curList ->
                sliderAdapter.setList(curList)
            }
            sliderHelper.submitList(ArrayList(sliderList))
            sliderHelper.setCurrentItem(1)
            setupAutoSlider()
        }
    }

    private fun handleClickEvents() {
        sliderAdapter.apply {
            setOnItemClickListener { currentItem ->
                currentItem.id?.let {
                    val bundle = Bundle().apply {
                        putInt(MOVIE_BUNDLE_KEY, it)
                    }
                    customNavigate(R.id.discoverFragment_to_filmDetailFragment, bundle)
                } ?: run {
                    Toast.makeText(requireContext(), "Sorry, Unexpected Error", Toast.LENGTH_SHORT)
                        .show()
                }


            }
        }
    }

}