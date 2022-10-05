package com.example.movieapp.data.model.search

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
data class Search(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<Results> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)
