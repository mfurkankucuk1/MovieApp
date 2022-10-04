package com.example.movieapp.utils

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import kotlinx.coroutines.delay
import timber.log.Timber

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
class SliderHelper(
    var context: Context,
    var llIndicator: LinearLayout,
    var viewPagerSlider: ViewPager2,
    var isCirclerSlider: Boolean = true,
    var indicatorVisibility: Boolean = true
) {

    private var _currentList: ArrayList<Any> = ArrayList()
    val currentList get() = _currentList
    private var _currentItemPosition = 0
    val currentItemPosition get() = _currentItemPosition
    var isPlay = false
    private var setPos = 0
    private var chageImage = false

    init {
        initSliderHelper()
    }

    private fun initSliderHelper() {

        viewPagerSlider.onPageChangeCallbackTry(
            onPageSelected = { position ->
                if (isCirclerSlider && position == 0) {
                    setPos = if (isCirclerSlider) currentList.size - 2 else 0
                    chageImage = true
                } else if (isCirclerSlider && position == _currentList.size - 1) {
                    setPos = if (isCirclerSlider) 1 else _currentList.size - 1
                    chageImage = true
                } else {
                    setPos = position
                    chageImage = false
                }

                if (chageImage) {
                    try {
                        viewPagerSlider.setCurrentItem(setPos, true)
                    } catch (e: Exception) {
                        Timber.e("context: $context setPos: $setPos Exception")
                        e.printStackTrace()
                        viewPagerSlider.setCurrentItem(_currentItemPosition, false)
                    }
                }
                _currentItemPosition = setPos
                setCurrentSliderIndicator(
                    setPos
                )
            },
            onPageScrollStateChanged = { state ->
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    onPageChangeCallbackListener?.let {
                        it(setPos)
                    }
                }
            }
        )
    }

    private fun setupSliderIndicators() {
        llIndicator.removeAllViews()
        val indicators =
            arrayOfNulls<ImageView>(if (isCirclerSlider) currentList.size - 2 else currentList.size)
        if (indicatorVisibility && indicators.size > 1) {
            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(8, 0, 8, 0)
            for (i in indicators.indices) {
                indicators[i] = ImageView(context)
                indicators[i]?.let {
                    it.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.slider_unselect_indicator
                        )
                    )
                    it.layoutParams = layoutParams
                    llIndicator.addView(it)
                }
            }
            llIndicator.show()
            setCurrentSliderIndicator(currentItemPosition)
        }
    }

    suspend fun startSlider(
        sliderTime: Long
    ) {
        isPlay = true
        while (isPlay && currentList.size > if (isCirclerSlider) 3 else 1) {
            _currentItemPosition = if (isCirclerSlider) 1 else 0
            while (currentItemPosition < currentList.size - 1) {
                delay(sliderTime)
                setCurrentSliderIndicator(currentItemPosition)
                viewPagerSlider.setCurrentItem(currentItemPosition, true)
                _currentItemPosition++
            }
        }
    }

    fun submitList(list: ArrayList<Any>) {
        _currentList.clear()
        if (isCirclerSlider) _currentList.add(ArrayList<Any>())
        _currentList.addAll(list)
        if (isCirclerSlider) _currentList.add(ArrayList<Any>())
        _currentItemPosition = if (isCirclerSlider) 1 else 0
        onSubmitListener?.let {
            it(currentList)
        }
        setupSliderIndicators()
    }

    fun setCurrentItem(item: Int, smoothScroll: Boolean = false) {
        _currentItemPosition = if (_currentList.size > item) item else 0
        viewPagerSlider.setCurrentItem(currentItemPosition, smoothScroll)
    }

    fun setCurrentSliderIndicator(position: Int) {
        val childCount = llIndicator.childCount
        var i = 0
        while (i < childCount && childCount == if (isCirclerSlider) currentList.size - 2 else currentList.size) {
            val imageView = llIndicator.getChildAt(i) as ImageView
            if (i == if (isCirclerSlider) position - 1 else position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.slider_unselect_indicator
                    )
                )
            }
            i++
        }
    }

    fun stopSlider() {
        isPlay = false
    }

    private var onPageChangeCallbackListener: ((position: Int) -> Unit)? = null

    fun setOnPageChangeCallbackListener(listener: (position: Int) -> Unit) {
        onPageChangeCallbackListener = listener
    }

    private var onSubmitListener: ((l: ArrayList<Any>) -> Unit)? = null

    fun setOnSubmitListener(listener: (l: ArrayList<Any>) -> Unit) {
        onSubmitListener = listener
    }


}