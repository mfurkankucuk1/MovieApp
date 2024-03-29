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

    suspend fun getGenres(apiKey: String, language: String) =
        filmService.getGenres(apiKey = apiKey, language = language)

    suspend fun getMovieList(listId: Int, language: String) =
        filmService.getMovieList(id = listId, apiKey = API_KEY, language = language)

    suspend fun getMovieSearch(searchText: String, language: String) =
        filmService.getMovieSearch(apiKey = API_KEY, language = language, query = searchText)

    suspend fun getMovieVideo(movieId: Int, language: String) =
        filmService.getMovieVideo(id = movieId, apiKey = API_KEY, language = language)

}