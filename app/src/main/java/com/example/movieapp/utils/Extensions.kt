package com.example.movieapp.utils

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/


fun ViewPager2.onPageChangeCallbackTry(
    onPageScrolled: ((p: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((s: Int) -> Unit)? = null,
    onPageSelected: ((p: Int) -> Unit)? = null
) {
    this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            onPageScrolled?.let {
                it(position)
            }

        }

        override fun onPageScrollStateChanged(state: Int) {

            super.onPageScrollStateChanged(state)
            onPageScrollStateChanged?.let {
                it(state)
            }

        }

        override fun onPageSelected(position: Int) {

            super.onPageSelected(position)
            onPageSelected?.let {
                it(position)
            }

        }
    })


}

fun setTouchable(activity: Activity, setTouchable: Boolean) {
    if (setTouchable) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    } else {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}

fun ImageView.load(image: String?) {
    Glide.with(this.context)
        .load(image)
        .into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.remove() {
    this.visibility = View.GONE
}

fun Fragment.customNavigate(navigationId:Int,bundle:Bundle?){
    bundle?.let {
        findNavController().navigate(navigationId,it)
    }?: run {
        findNavController().navigate(navigationId)
    }
}
