/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.udacityshoestore.views.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.esoapps.udacityshoestore.R
import com.esoapps.udacityshoestore.databinding.AddShoeFragBinding
import com.esoapps.udacityshoestore.viewmodels.MainViewModel


class AddShoeFrag : Fragment() {

    private var binding: AddShoeFragBinding? = null

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_shoe_frag, container, false)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.viewModel = viewModel
        binding!!.lifecycleOwner = this //viewLifeCycleOwner//IMPORTANT TO MAKE VIEW MODEL CYCLE RELATED TO CURRENT VIEW CYCLE



        viewModel.doneAddingData.observe(viewLifecycleOwner,
            Observer { doneAddingData ->
                if (doneAddingData) {

                    val action = AddShoeFragDirections.actionAddShoeFragToStoreListFrag()
                    view?.findNavController()?.navigate(action)

                    viewModel.doneAddingData.value = false
                }

            })


    }


}
