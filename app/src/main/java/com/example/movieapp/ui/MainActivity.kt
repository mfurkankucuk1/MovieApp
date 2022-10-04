package com.example.movieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.utils.remove
import com.example.movieapp.utils.setTouchable
import com.example.movieapp.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }


     fun showLoading() {
        binding.apply {
            setTouchable(this@MainActivity, false)
            llLoading.show()
            animationLoading.playAnimation()
        }
    }

     fun hideLoading() {
        binding.apply {
            setTouchable(this@MainActivity, true)
            llLoading.remove()
            animationLoading.pauseAnimation()
        }
    }

     fun hideBottomNavigation() {
        binding.apply {
            bottomNavigation.remove()
        }

    }

     fun showBottomNavigation() {
        binding.apply {
            bottomNavigation.show()
        }
    }

}