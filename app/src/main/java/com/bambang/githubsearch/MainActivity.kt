package com.bambang.githubsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bambang.githubsearch.app.SearchApplication
import com.bambang.githubsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val mainComponent: MainComponent by lazy { buildMainComponent() }
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
    }

    private fun buildMainComponent(): MainComponent = DaggerMainComponent.builder()
        .appComponent((application as SearchApplication).appComponent)
        .build()
}