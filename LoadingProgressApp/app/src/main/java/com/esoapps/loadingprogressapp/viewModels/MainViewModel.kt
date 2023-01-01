/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.loadingprogressapp.viewModels

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esoapps.loadingprogressapp.R
import com.esoapps.loadingprogressapp.model.Downloading
import com.esoapps.loadingprogressapp.utils.Constants.Companion.GLIDE_APP_URL
import com.esoapps.loadingprogressapp.utils.Constants.Companion.MAIN_APP_URL
import com.esoapps.loadingprogressapp.utils.Constants.Companion.RETROFIT_APP_URL

//ONLY PASSING APPLICATION THROUGH THE FACTORY TO GET RESOURCES STRING:
class MainViewModel(private val application: Application):ViewModel() {


    //CONSTANTS:(NO CHANGE REQUIRED) RADIOS BUTTONS:
    val glideDownloading=MutableLiveData(Downloading(
        application.resources.getString(R.string.glide_url_title),
        GLIDE_APP_URL,""))

    val loadingAppDownloading=MutableLiveData(Downloading(
        application.resources.getString(R.string.loading_url_title),
        MAIN_APP_URL,""))

    val retrofitDownloading=MutableLiveData(Downloading(
        application.resources.getString(R.string.retrofit_url_title),
        RETROFIT_APP_URL,""))

    val customDownloading=MutableLiveData(Downloading(
        application.resources.getString(R.string.custom_url_title),
        "",""))


    //SET AND GET DOWNLOADING DATA:
    private var _chosenDownloading = MutableLiveData<Downloading?>(Downloading())
    val chosenDownloading: LiveData<Downloading?> get() = _chosenDownloading

    //EDT TXT:
    val customDownloadingFromEdt: LiveData<Downloading?> = MutableLiveData<Downloading?>(Downloading(
        "","",""))

    //TO BE AWARE OF CHOSEN RADIO BUTTON SO IF IT EDIT TEXT GET URL
    private var _chosenRadioBtn = MutableLiveData<Int?>(0)
    val chosenRadioBtn: LiveData<Int?> get() = _chosenRadioBtn


    //CONTROL EDIT TEXT VISIBILITY:
    private var _showMeEdt = MutableLiveData<Boolean?>(false)
    val showMeEdt: LiveData<Boolean?> get() = _showMeEdt

    fun changeCheckedRadioBtn(view:View): Boolean {
        if (view is RadioButton){
            //val checked = view.isChecked
            view.isChecked=true


            var checkedRadioButtonId=view.id

            when (checkedRadioButtonId) {
                R.id.glideRadioBtn -> {
                    _showMeEdt.value=false
                    _chosenDownloading.value = glideDownloading.value

                    _chosenRadioBtn.value =1
                }
                R.id.loadingRadioBtn -> {
                    _showMeEdt.value=false
                    _chosenDownloading.value = loadingAppDownloading.value

                    _chosenRadioBtn.value =2
                }
                R.id.retrofitRadioBtn -> {
                    _showMeEdt.value=false
                    _chosenDownloading.value = retrofitDownloading.value

                    _chosenRadioBtn.value =3
                }

                R.id.customRadioBtn -> {

                    _showMeEdt.value=true

                    _chosenRadioBtn.value =4

                }

            }


        }

        return true
    }





}