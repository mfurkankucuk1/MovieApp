package com.example.movieapp.data.model.moviedetail

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
data class MovieDetailGenres(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null
)