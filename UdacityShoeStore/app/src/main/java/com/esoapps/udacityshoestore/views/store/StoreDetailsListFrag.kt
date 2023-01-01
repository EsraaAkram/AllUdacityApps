/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.udacityshoestore.views.store


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.esoapps.udacityshoestore.R
import com.esoapps.udacityshoestore.databinding.StoreDetailsListFragBinding
import com.esoapps.udacityshoestore.databinding.StoreItemRowBinding
import com.esoapps.udacityshoestore.viewmodels.MainViewModel


class StoreDetailsListFrag : Fragment(), Toolbar.OnMenuItemClickListener {

    private val viewModel: MainViewModel by activityViewModels()

    private var binding: StoreDetailsListFragBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //binding = StoreListFragBinding.inflate(inflater,container,false)

        binding= DataBindingUtil.inflate(inflater, R.layout.store_details_list_frag, container, false)

        return binding?.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.addFloatList?.setOnClickListener{

            //binding?.addFloatList?.findNavController()?.navigate(R.id.action_storeListFrag_to_addShoeFrag)

            val action = StoreDetailsListFragDirections.actionStoreListFragToAddShoeFrag()
            view?.findNavController()?.navigate(action)

        }



        //LOG OUT AND MENU:
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        var toolbar = view.findViewById<Toolbar>(R.id.toolbarList)
        toolbar.title = getString(R.string.app_name)
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(),R.color.c_white))
        toolbar.inflateMenu(R.menu.menu)


        toolbar.setOnMenuItemClickListener(this)






        //OBSERVE AND ADD DATA TO LAYOUT:
        viewModel.shoesList.observe(viewLifecycleOwner, Observer { shoesList ->


            //RESET SHOES DATA VALUES:
            viewModel.clearShoesData()

            shoesList.forEach{shoesItem->

                Log.d("viewModel",shoesItem.getName())

                val storeBinding = StoreItemRowBinding.inflate(layoutInflater)
                storeBinding.shoesData=shoesItem


                if (storeBinding.root != null) {
                    storeBinding.root.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(15, 10, 15, 30)
                    storeBinding.root.layoutParams = params

                    binding?.innerLinearList?.addView(storeBinding.root)


                }


            }

        })
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {

        when (menuItem?.itemId) {
            R.id.logout ->     {
                val action = StoreDetailsListFragDirections.actionStoreListFragToLoginFrag()
                view?.findNavController()?.navigate(action)

                return true
            }



        }
        return false
    }


}