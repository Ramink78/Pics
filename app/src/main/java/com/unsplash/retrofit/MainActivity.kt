package com.unsplash.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.home.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        navController = Navigation.findNavController(this, R.id.host)
        // navController.navigate(R.id.action_navigation_explore_to_navigation_home)
        NavigationUI.setupWithNavController(nav, navController)


    }

}