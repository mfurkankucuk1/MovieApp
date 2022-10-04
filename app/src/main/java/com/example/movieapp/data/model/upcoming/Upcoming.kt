package com.example.movieapp.data.model.upcoming

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
data class Upcoming(
    @SerializedName("dates") var dates: UpcomingDates? = UpcomingDates(),
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<UpcomingResults> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null

)
