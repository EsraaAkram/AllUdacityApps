package com.esoapps.asteroidradarnasa.views.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.esoapps.asteroidradarnasa.R
import com.esoapps.asteroidradarnasa.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    //private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }

    override fun onSupportNavigateUp(): Boolean {
        var navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}