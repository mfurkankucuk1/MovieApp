package com.example.movieapp.data.model.video

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
data class Video(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("results") var results: ArrayList<VideoResult> = arrayListOf()

)
