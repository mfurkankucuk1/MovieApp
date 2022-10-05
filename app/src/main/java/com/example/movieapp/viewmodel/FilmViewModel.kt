package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.utils.NetworkHelper
import com.example.movieapp.common.utils.NetworkResult
import com.example.movieapp.common.utils.RequestHelper
import com.example.movieapp.data.model.filmlist.FilmList
import com.example.movieapp.data.model.genres.GenresResponse
import com.example.movieapp.data.model.moviedetail.MovieDetail
import com.example.movieapp.data.model.upcoming.Upcoming
import com.example.movieapp.data.repository.FilmRepository
import com.example.movieapp.utils.Constants.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
@HiltViewModel
class FilmViewModel @Inject constructor(
    application: Application,
    private val filmRepository: FilmRepository
) : AndroidViewModel(application) {

    private fun getLocalLanguage(): String {
        return Locale.getDefault().toLanguageTag()
    }

    private var _upcomingFilmResponse: MutableLiveData<NetworkResult<Upcoming>> =
        MutableLiveData()
    val upcomingFilmResponse: LiveData<NetworkResult<Upcoming>> get() = _upcomingFilmResponse

    private var _movieDetailResponse: MutableLiveData<NetworkResult<MovieDetail>> =
        MutableLiveData()
    val movieDetailResponse: LiveData<NetworkResult<MovieDetail>> get() = _movieDetailResponse

    private var _genresResponse: MutableLiveData<NetworkResult<GenresResponse>> = MutableLiveData()
    val genresResponse: LiveData<NetworkResult<GenresResponse>> get() = _genresResponse

    private var _movieListResponse: MutableLiveData<NetworkResult<FilmList>> = MutableLiveData()
    val movieListResponse: LiveData<NetworkResult<FilmList>> get() = _movieListResponse


    fun clearMovieListResponse(){
        _movieListResponse.value = null
    }

    fun clearGenresResponse() {
        _genresResponse.value = null
    }

    fun clearUpcomingFilmResponse() {
        _upcomingFilmResponse.value = null
    }

    fun clearMovieDetailResponse() {
        _movieDetailResponse.value = null
    }

    fun getMovieList(listId:Int) = viewModelScope.launch {
        getMovieListSafeCall(listId)
    }

    private suspend fun getMovieListSafeCall(listId: Int) {
        _movieListResponse.value = NetworkResult.Loading(true)
        if (NetworkHelper.hasInternetConnection(getApplication())) {
            try {
                val response =
                    filmRepository.getMovieList(listId = listId,getLocalLanguage())
                _movieListResponse.value = RequestHelper.handleResponse(response)
            } catch (e: Exception) {
                _movieListResponse.value = NetworkResult.Error(e.message.toString())
                e.printStackTrace()
            }
        } else {
            _movieListResponse.value = NetworkResult.Error("Check your internet connection")
        }
    }

    fun getGenres() = viewModelScope.launch {
        getGenresSafeCall()
    }

    private suspend fun getGenresSafeCall() {
        _genresResponse.value = NetworkResult.Loading(true)
        if (NetworkHelper.hasInternetConnection(getApplication())) {
            try {
                val response =
                    filmRepository.getGenres(API_KEY, getLocalLanguage())
                _genresResponse.value = RequestHelper.handleResponse(response)
            } catch (e: Exception) {
                _genresResponse.value = NetworkResult.Error(e.message.toString())
                e.printStackTrace()
            }
        } else {
            _genresResponse.value = NetworkResult.Error("Check your internet connection")
        }
    }


    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        getMovieDetailSafeCall(movieId)
    }

    private suspend fun getMovieDetailSafeCall(movieId: Int) {
        _movieDetailResponse.value = NetworkResult.Loading(true)
        if (NetworkHelper.hasInternetConnection(getApplication())) {
            try {
                val response =
                    filmRepository.getMovieDetail(movieId, getLocalLanguage())
                _movieDetailResponse.value = RequestHelper.handleResponse(response)
            } catch (e: Exception) {
                _movieDetailResponse.value = NetworkResult.Error(e.message.toString())
                e.printStackTrace()
            }
        } else {
            _movieDetailResponse.value = NetworkResult.Error("Check your internet connection")
        }
    }

    fun getUpcomingFilms() = viewModelScope.launch {
        getUpcomingFilmListSafeCall()
    }

    private suspend fun getUpcomingFilmListSafeCall() {
        _upcomingFilmResponse.value = NetworkResult.Loading(true)
        if (NetworkHelper.hasInternetConnection(getApplication())) {
            try {
                val response =
                    filmRepository.getUpcomingList(getLocalLanguage())
                _upcomingFilmResponse.value = RequestHelper.handleResponse(response)
            } catch (e: Exception) {
                _upcomingFilmResponse.value = NetworkResult.Error(e.message.toString())
                e.printStackTrace()
            }
        } else {
            _upcomingFilmResponse.value = NetworkResult.Error("Check your internet connection")
        }
    }

}