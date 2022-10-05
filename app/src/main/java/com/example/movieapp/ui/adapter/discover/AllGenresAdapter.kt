package com.example.movieapp.ui.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.genres.Genres
import com.example.movieapp.databinding.ItemRowGenresBinding

/**
 * Created by M.Furkan KÜÇÜK on 5.10.2022
 **/
class AllGenresAdapter : RecyclerView.Adapter<AllGenresAdapter.AllGenresViewHolder>() {

    inner class AllGenresViewHolder(val binding: ItemRowGenresBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Genres>() {
        override fun areItemsTheSame(
            oldItem: Genres,
            newItem: Genres
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Genres,
            newItem: Genres
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllGenresViewHolder {
        return AllGenresViewHolder(
            ItemRowGenresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AllGenresViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            tvGenresName.text = currentItem.name
            if (currentItem.isSelected) {
                tvGenresName.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.text_color
                    )
                )
                root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.white
                    )
                )
            } else {
                tvGenresName.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.white
                    )
                )
                root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.text_color
                    )
                )
            }

            root.setOnClickListener {
                onItemClickListener?.let {
                    it(currentItem,position)
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((currentItem: Genres, position: Int) -> Unit?)? = null

    fun setOnItemClickListener(listener: ((currentItem: Genres, position: Int) -> Unit)) {
        onItemClickListener = listener
    }

}