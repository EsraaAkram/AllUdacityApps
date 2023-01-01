/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.udacityshoestore.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esoapps.udacityshoestore.models.Shoe

class MainViewModel : ViewModel() {


    private var mutableShoesList = MutableLiveData<MutableList<Shoe>>(mutableListOf())
//    private var mutableShoesList = MutableLiveData<MutableList<Shoe>>()
    val shoesList: LiveData<MutableList<Shoe>> get() = mutableShoesList


    init {
//        mutableShoesList.value=ArrayList()
    }

    var name = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var company = MutableLiveData<String>()
    var size = MutableLiveData<String>()


    var doneAddingData = MutableLiveData<Boolean>()

    fun saveData(

    ) {

        if (name.value?.trim()?.isEmpty() == false
            &&
            description.value?.trim()?.isEmpty() == false
            &&
            company.value?.trim()?.isEmpty() == false
            &&
            size.value?.trim()?.isEmpty() == false
        ){

            addShoesToList(
                Shoe(
                    name = name.value.toString(),
                    description = description.value.toString(),
                    company = company.value.toString(),
                    //size = 0.0
                    size = size.value.toString().trim().toDouble() ?: 0.0
                )
            ).run {

                //CLEAR SHOES DATA LISTENERS:
                doneAddingData.value=true
                //clearShoesData()

            }
        }


    }



    fun cancelAddingData(){

        doneAddingData.value=true

    }

    fun addShoesToList(shoe: Shoe) {

        mutableShoesList.value?.add(shoe)
        Log.d("viewModel0", mutableShoesList.value?.size.toString())
        Log.d("viewModel1", shoesList.value.toString())

    }


    fun clearShoesData(){

        name.value = ""
        description.value = ""
        company.value = ""
        size.value = ""

    }

}