package com.example.movieapp.data.model.moviedetail

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
data class ProductionCountries(
    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null
)
