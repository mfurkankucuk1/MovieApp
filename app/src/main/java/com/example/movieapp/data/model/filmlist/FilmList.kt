package com.example.movieapp.data.model.filmlist

import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
data class FilmList(
    @SerializedName("created_by") var createdBy: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("favorite_count") var favoriteCount: Int? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("items") var items: ArrayList<Items> = arrayListOf(),
    @SerializedName("item_count") var itemCount: Int? = null,
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null
)