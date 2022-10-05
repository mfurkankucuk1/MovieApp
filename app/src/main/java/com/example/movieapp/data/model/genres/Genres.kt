package com.example.movieapp.data.model.genres

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
data class Genres(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    var isSelected :Boolean
)