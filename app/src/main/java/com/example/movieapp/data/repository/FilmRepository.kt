package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.FilmService
import com.example.movieapp.utils.Constants.API_KEY
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
class FilmRepository @Inject constructor(private val filmService: FilmService) {

    suspend fun getUpcomingList(language: String) =
        filmService.getUpcomingList(apiKey = API_KEY, language = language)

    suspend fun getMovieDetail(movieId: Int, language: String) =
        filmService.getMovieDetail(id = movieId, apiKey = API_KEY, language = language)

}