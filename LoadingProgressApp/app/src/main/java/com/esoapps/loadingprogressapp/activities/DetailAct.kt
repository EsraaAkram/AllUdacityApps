/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.loadingprogressapp.activities

import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.esoapps.loadingprogressapp.R
import com.esoapps.loadingprogressapp.databinding.DetailActBinding
import com.esoapps.loadingprogressapp.model.Downloading
import com.esoapps.loadingprogressapp.utils.Constants.Companion.DOWNLOAD_FILE_NAME_KEY
import com.esoapps.loadingprogressapp.utils.Constants.Companion.DOWNLOAD_STATUS_KEY


class DetailAct : AppCompatActivity() {

    private var binding: DetailActBinding? = null

    private var chosenDownloadingData: Downloading = Downloading(
        //WILL BE EDITED LATER BY INTENT, I USED UNKNOWN AS A DEFAULT IN CASE USER ENTERED URL
        "UnKnown",
        "",
        ""
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.detail_act)
        setSupportActionBar(binding?.toolbarDetail)

        if (intent.extras != null) {
            handleNotificationIntent()
        }

        binding?.downloading = chosenDownloadingData

        itemsClicks()

        //goBack()//NO NEED I HANDLED MAIN AS SINGLE INSTANCE SO BACK STACK HANDLED


        //CANCEL NOTIFICATIONS AFTER USER CLICKED IT:
        var notificationManager = ContextCompat.getSystemService(
            this@DetailAct,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.cancelAll()

    }


    private fun handleNotificationIntent() {

        chosenDownloadingData.title = getStringFromNotification(DOWNLOAD_FILE_NAME_KEY)
        chosenDownloadingData.status = getStringFromNotification(DOWNLOAD_STATUS_KEY)


    }

    private fun getStringFromNotification(requiredStringKey: String): String {
        var requiredStringValue = if (intent!!.extras!!.containsKey(requiredStringKey)) {
            intent!!.extras!!.getString(requiredStringKey).toString()
        } else {
            " "
        }

        return requiredStringValue
    }


    private fun itemsClicks() {

        binding?.contentDetail?.okBtnDetailAct?.setOnClickListener {
            backToMain()
        }

    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //IF USER OPEN AND USE THE APP AND GET NOTIFICATION:
        //THIS METHOD WILL HANDLE THAT CASE
        if (intent != null) {
            if (intent.extras != null) {
                handleNotificationIntent()
            }
        }
    }


    private fun goBack() {

        //ON BACK PRESSED (DEPRECATED)
        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {

                backToMain()
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {

                        backToMain()
                    }
                })
        }

    }


    fun backToMain() {

        startActivity(Intent(this@DetailAct, MainAct::class.java))
        finish()

    }

}
