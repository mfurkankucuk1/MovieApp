package com.example.movieapp.data.model.moviedetail

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
data class ProductionCompanies(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("logo_path") var logoPath: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("origin_country") var originCountry: String? = null
)
