package com.example.movieapp.ui.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.filmlist.Items
import com.example.movieapp.databinding.ItemRowMovieListBinding
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.load

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    inner class MovieListViewHolder(val binding: ItemRowMovieListBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(
            oldItem: Items,
            newItem: Items
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Items,
            newItem: Items
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            ItemRowMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            tvMovieName.text = currentItem.title
            imgMovie.load("${Constants.IMAGE_URL}${currentItem.posterPath}")
            root.setOnClickListener {
                onItemClickListener?.let {
                    it(currentItem)
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size
    private var onItemClickListener: ((currentItem: Items) -> Unit?)? = null

    fun setOnItemClickListener(listener: ((currentItem: Items) -> Unit)) {
        onItemClickListener = listener
    }
}