package com.example.movieapp.ui.adapter.discover

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.upcoming.UpcomingResults
import com.example.movieapp.databinding.ItemRowSliderBinding
import com.example.movieapp.utils.Constants.IMAGE_URL
import com.example.movieapp.utils.load

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
class SliderAdapter : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(val binding: ItemRowSliderBinding) :
        RecyclerView.ViewHolder(binding.root)


    private var _list: ArrayList<Any> = ArrayList()

    /** Slider */
    val list: ArrayList<Any> get() = _list

    @SuppressLint("NotifyDataSetChanged")
    fun setList(l: ArrayList<Any>) {
        _list.clear()
        l.forEach { item ->
            _list.add(item)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            ItemRowSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
            if ( list[position] is UpcomingResults){
                val currentItem = list[position] as UpcomingResults
                holder.binding.apply {
                    imgSlider.load("${IMAGE_URL}${currentItem.posterPath}")
                    mcvSlider.setOnClickListener {
                        onItemClickListener?.let {
                            it(currentItem)
                        }
                    }
                }
            }

    }


    override fun getItemCount() = list.size

    private var onItemClickListener: ((currentItem: UpcomingResults) -> Unit?)? = null

    fun setOnItemClickListener(listener: ((currentItem: UpcomingResults) -> Unit)) {
        onItemClickListener = listener
    }
}