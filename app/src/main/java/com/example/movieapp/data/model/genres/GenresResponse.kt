package com.example.movieapp.data.model.genres

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
data class GenresResponse(
    @SerializedName("genres") var genres : ArrayList<Genres> = arrayListOf()
)
