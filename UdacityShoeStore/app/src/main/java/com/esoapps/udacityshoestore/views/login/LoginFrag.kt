/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.udacityshoestore.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.esoapps.udacityshoestore.R
import com.esoapps.udacityshoestore.databinding.LoginFragBinding

class LoginFrag : Fragment() {

    private var binding: LoginFragBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater, R.layout.login_frag, container, false)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.signInBtn?.setOnClickListener {

            checkMailAndPassWord(binding!!.signInBtn)

        }


        binding?.signUpBtn?.setOnClickListener {

            checkMailAndPassWord(binding!!.signUpBtn)
        }
    }

    private fun checkMailAndPassWord(signBtn: Button?) {

        if(binding?.mailEdt?.text?.trim()?.isEmpty() == false
            &&
            binding?.passEdt?.text?.trim()?.isEmpty() == false){

            if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding?.mailEdt?.text.toString()).matches()){

                val action = LoginFragDirections.actionLoginFragToOnBoardingFrag()
                view?.findNavController()?.navigate(action)


            }else{
                Toast.makeText(context,getString(R.string.wrong_mail_toast_msg),Toast.LENGTH_SHORT).show()
            }

        }else{

            Toast.makeText(context,getString(R.string.wrong_mail_or_pass_toast_msg),Toast.LENGTH_SHORT).show()
        }




    }


}