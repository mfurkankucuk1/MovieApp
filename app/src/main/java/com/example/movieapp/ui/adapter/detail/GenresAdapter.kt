package com.example.movieapp.ui.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.moviedetail.MovieDetailGenres
import com.example.movieapp.databinding.ItemRowGenresBinding

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
class GenresAdapter : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    inner class GenresViewHolder(val binding: ItemRowGenresBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<MovieDetailGenres>() {
        override fun areItemsTheSame(
            oldItem: MovieDetailGenres,
            newItem: MovieDetailGenres
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieDetailGenres,
            newItem: MovieDetailGenres
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder(
            ItemRowGenresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            tvGenresName.text = currentItem.name
        }
    }

    override fun getItemCount() = differ.currentList.size
}