package com.example.movieapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.common.utils.NetworkResult
import com.example.movieapp.data.model.moviedetail.MovieDetail
import com.example.movieapp.databinding.FragmentFilmDetailBinding
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.adapter.detail.GenresAdapter
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.load
import com.example.movieapp.viewmodel.FilmViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
@AndroidEntryPoint
class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding: FragmentFilmDetailBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val filmViewModel: FilmViewModel by viewModels()
    private val genresAdapter: GenresAdapter by lazy { GenresAdapter() }
    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }

    private fun handleArguments() {
        arguments?.let {
            movieId = it.get(Constants.MOVIE_BUNDLE_KEY) as Int
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupAdapters()
        subscribeObservers()
        handleClickEvents()
    }

    private fun initialize() {
        mainActivity = requireActivity() as MainActivity
        mainActivity.hideBottomNavigation()
        movieId?.let { id ->
            filmViewModel.getMovieDetail(id)
        } ?: run {
            Toast.makeText(requireContext(), "Sorry, Unexpected Error", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

    }

    private fun setupAdapters() {
        binding.apply {
            rvGenres.apply {
                adapter = genresAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun subscribeObservers() {
        filmViewModel.movieDetailResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Loading -> {
                    mainActivity.showLoading()
                }
                is NetworkResult.Error -> {
                    mainActivity.hideLoading()
                }
                is NetworkResult.Success -> {
                    response?.data?.let { result ->
                        handleDetailResponse(result)
                    }
                    mainActivity.hideLoading()
                    filmViewModel.clearMovieDetailResponse()
                }
            }
        }
    }

    private fun handleDetailResponse(data: MovieDetail) {
        binding.apply {
            imgMovie.load("${Constants.IMAGE_URL}${data.posterPath}")
            tvMovieName.text = data.title
            tvMovieDescription.text = data.overview
            genresAdapter.differ.submitList(data.genres)
            tvVoteCount.text = "(${data.voteCount})"
            data.voteAverage?.let {
                ratingProduct.rating =  it.toFloat()
            } ?: run {
                ratingProduct.rating =  0f
            }

        }
    }

    private fun handleClickEvents() {
        binding.apply {
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}