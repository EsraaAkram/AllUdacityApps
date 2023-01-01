/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.udacityshoestore

import android.accessibilityservice.GestureDescription
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import com.esoapps.udacityshoestore.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}