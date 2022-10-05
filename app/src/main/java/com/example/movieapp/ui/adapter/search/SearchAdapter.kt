package com.example.movieapp.ui.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.filmlist.Items
import com.example.movieapp.data.model.search.Results
import com.example.movieapp.databinding.ItemRowMovieListBinding
import com.example.movieapp.ui.adapter.discover.MovieListAdapter
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.load

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MovieListViewHolder>() {

    inner class MovieListViewHolder(val binding: ItemRowMovieListBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(
            oldItem: Results,
            newItem: Results
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Results,
            newItem: Results
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
    private var onItemClickListener: ((currentItem: Results) -> Unit?)? = null

    fun setOnItemClickListener(listener: ((currentItem: Results) -> Unit)) {
        onItemClickListener = listener
    }
}