/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.udacityshoestore.views.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.esoapps.udacityshoestore.R
import com.esoapps.udacityshoestore.databinding.OnBoardingWelcomeFragBinding

class OnBoardingWelcomeFrag : Fragment() {

    private var binding: OnBoardingWelcomeFragBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //binding = OnBoardingWelcomeFragBinding.inflate(inflater,container,false)
        binding= DataBindingUtil.inflate(inflater, R.layout.on_boarding_welcome_frag, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.nextBtnBoard?.setOnClickListener {

            //binding?.nextBtnBoard?.findNavController()?.navigate(R.id.action_onBoardingFrag_to_InstructionsFrag)

            val action = OnBoardingWelcomeFragDirections.actionOnBoardingFragToInstructionsFrag()
            view?.findNavController()?.navigate(action)
        }
    }





}