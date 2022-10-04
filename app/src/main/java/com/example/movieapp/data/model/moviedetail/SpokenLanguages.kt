package com.example.movieapp.data.model.moviedetail

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
data class SpokenLanguages(
    @SerializedName("english_name") var englishName: String? = null,
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("name") var name: String? = null
)
