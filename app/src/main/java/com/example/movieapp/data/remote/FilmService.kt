package com.example.movieapp.data.remote

import com.example.movieapp.data.model.filmlist.FilmList
import com.example.movieapp.data.model.genres.GenresResponse
import com.example.movieapp.data.model.moviedetail.MovieDetail
import com.example.movieapp.data.model.search.Search
import com.example.movieapp.data.model.upcoming.Upcoming
import com.example.movieapp.data.model.video.Video
import com.example.movieapp.utils.Constants.API_KEY_QUERY
import com.example.movieapp.utils.Constants.LANGUAGE_QUERY
import com.example.movieapp.utils.Constants.PAGE_QUERY
import com.example.movieapp.utils.Constants.QUERY
import com.example.movieapp.utils.EndPoints.FILM_LIST
import com.example.movieapp.utils.EndPoints.GENRES
import com.example.movieapp.utils.EndPoints.MOVIE_DETAIL
import com.example.movieapp.utils.EndPoints.MOVIE_SEARCH
import com.example.movieapp.utils.EndPoints.UPCOMING_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
interface FilmService {

    @GET(UPCOMING_LIST)
    suspend fun getUpcomingList(
        @Query(API_KEY_QUERY) apiKey: String,
        @Query(LANGUAGE_QUERY) language: String,
        @Query(
            PAGE_QUERY
        ) page: Int = 1
    ): Response<Upcoming>

    @GET("${MOVIE_DETAIL}/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
        @Query(API_KEY_QUERY) apiKey: String,
        @Query(LANGUAGE_QUERY) language: String
    ): Response<MovieDetail>


    @GET(GENRES)
    suspend fun getGenres(
        @Query(API_KEY_QUERY) apiKey: String,
        @Query(LANGUAGE_QUERY) language: String
    ): Response<GenresResponse>

    @GET("${FILM_LIST}/{id}")
    suspend fun getMovieList(
        @Path("id") id: Int,
        @Query(API_KEY_QUERY) apiKey: String,
        @Query(LANGUAGE_QUERY) language: String
    ): Response<FilmList>

    @GET(MOVIE_SEARCH)
    suspend fun getMovieSearch(
        @Query(API_KEY_QUERY) apiKey: String,
        @Query(LANGUAGE_QUERY) language: String,
        @Query(QUERY) query: String
    ):Response<Search>


    @GET("${MOVIE_DETAIL}/{id}/videos")
    suspend fun getMovieVideo(
        @Path("id") id: Int,
        @Query(API_KEY_QUERY) apiKey: String,
        @Query(LANGUAGE_QUERY) language: String
    ): Response<Video>

}