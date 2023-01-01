/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.loadingprogressapp.activities

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.esoapps.loadingprogressapp.App
import com.esoapps.loadingprogressapp.R
import com.esoapps.loadingprogressapp.databinding.MainActBinding
import com.esoapps.loadingprogressapp.helpers.checkValidUrl
import com.esoapps.loadingprogressapp.helpers.isNetworkAvailable
import com.esoapps.loadingprogressapp.model.Downloading
import com.esoapps.loadingprogressapp.notifications.manager.prepareToShowNotification
import com.esoapps.loadingprogressapp.notifications.permission.PermissionHelperNormal
import com.esoapps.loadingprogressapp.viewModels.MainViewModel
import com.esoapps.loadingprogressapp.viewModels.MainViewModelFactory
import com.esoapps.loadingprogressapp.views.ButtonState


class MainAct : AppCompatActivity() {

    private var binding: MainActBinding? = null


    private val viewModel: MainViewModel by viewModels {
        val activity = requireNotNull(this@MainAct) {
            "You can only access the viewModel after onViewCreated()"
        }

        MainViewModelFactory(activity.application as App)

    }

    private var chosenDownloadingData: Downloading? = null

    //TO BE AWARE OF CHOSEN RADIO BUTTON SO IF IT EDIT TEXT GET URL
    private var lastChosenRadioBtn: Int = 0


    //ASK NOTIFICATIONS PERMISSION ONLY ANDROID 13
    private var permissionHelperNormal: PermissionHelperNormal? = null
    private var doneAskingForPermission = false


    private var downloadId: Long = 0


    private var downloadManager: DownloadManager? = null

    private val completeListenerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)


            if (id == downloadId) {

                binding?.contentMain?.customButton?.buttonState = ButtonState.Completed
                binding?.contentMain?.customButton?.isEnabled = true

                prepareToShowNotification(
                    applicationContext,
                    getString(R.string.notification_title),
                    getString(R.string.notification_description),
                    checkCurrentDownloadStatus(id),
                    chosenDownloadingData?.title.toString()//NEVER WILL BE NULL
                )


            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ASK NOTIFICATION PERMISSION:
        permissionHelperNormal = PermissionHelperNormal(
            this@MainAct,
            this@MainAct
        )

        doneAskingForPermission = permissionHelperNormal!!
            .askNotificationPermission()

        binding = DataBindingUtil.setContentView(this, R.layout.main_act)

        setSupportActionBar(binding?.toolbarMain)

        binding?.viewModel = viewModel

        if (doneAskingForPermission) {

            registerReceiver(
                completeListenerReceiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )

        }



        viewModelListeners()

        itemsClicks()


    }


    private fun viewModelListeners() {

        viewModel.chosenDownloading.observe(this, Observer { chosenDownloading ->

            if (chosenDownloading != null) {
                if (chosenDownloading.downloadUrl != null) {//MAKE SURE I HAVE URL

                    chosenDownloadingData = chosenDownloading


                }
            }
        })


        //CUSTOM URL:
        viewModel.showMeEdt.observe(this, Observer { showMeEdt ->

            if (binding?.contentMain?.downloadUrlEdt != null) {
                if (showMeEdt == true) {

                    binding?.contentMain?.downloadUrlEdt!!.visibility = View.VISIBLE
                } else {
                    //SO HIDE ON START:
                    binding?.contentMain?.downloadUrlEdt!!.visibility = View.GONE
                }
            }

        })

        //THIS OBSERVER MADE BECAUSE OF:
        //MAY USER SHOW EDIT TEXT AND WRITE ON IT BUT AFTER THAT CHANGE HIS MIND AND CHOOSE ANOTHER RADIO BTN
        viewModel.chosenRadioBtn.observe(this, Observer { chosenRadioBtn ->
            if (chosenRadioBtn != null) {
                lastChosenRadioBtn = chosenRadioBtn

                if (lastChosenRadioBtn == 4) {//SO USER USED EDT

                    chosenDownloadingData = viewModel.customDownloadingFromEdt.value

                }

            }

        })


    }

    private fun itemsClicks() {

        binding?.contentMain?.customButton?.setOnClickListener {


            if (isNetworkAvailable(this@MainAct)) {

                if (chosenDownloadingData != null) {//AT THIS POINT I'M SURE I HAVE URL BECAUSE I CHECKED IN OBSERVER


                    if (checkValidUrl(this@MainAct, chosenDownloadingData?.downloadUrl)) {

                        binding?.contentMain?.customButton?.buttonState = ButtonState.Clicked
                        binding?.contentMain?.customButton?.isEnabled = false


                        //DOWNLOAD:
                        downloadFromUrl()
                    }
                } else {
                    //INFORM USER TO SELECT FILE
                    Toast.makeText(
                        this@MainAct,
                        getString(R.string.select_file_toast_msg),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    this@MainAct,
                    getString(R.string.no_internet_toast_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }


    private fun downloadFromUrl() {

        var fileExtension = ""

        //ADDING EXTENSION TO DOWNLOADABLE FILE SO USER CAN OPEN IT:
        if (chosenDownloadingData?.downloadUrl.toString().contains(".")) {
            fileExtension =
                chosenDownloadingData?.downloadUrl?.toString()?.substringAfterLast(".").toString()

        }

        val request = DownloadManager.Request(Uri.parse(chosenDownloadingData?.downloadUrl))

            //ADDING EXTENSION TO DOWNLOADABLE FILE SO USER CAN OPEN IT:
            .setTitle(getString(R.string.app_name) + ".$fileExtension")
            .setDescription(getString(R.string.app_description))

            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        or DownloadManager.Request.NETWORK_MOBILE
            )

            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

            //TO NOTIFY FILE EXPLORER APP:
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                getString(R.string.app_name) + ".$fileExtension"
            )

            //NO NEED MY CUSTOM NOTIFICATIONS ADDED:
            //.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setRequiresCharging(false)
                }
            }

        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadId = downloadManager?.enqueue(request)!!


    }


    private fun checkCurrentDownloadStatus(downloadIdRef: Long): String {

        var downloadStatus: String = getString(R.string.download_status_failed)

        val downloadManagerQuery = DownloadManager.Query()
        downloadManagerQuery.setFilterById(downloadIdRef)

        val cursor: Cursor? = downloadManager?.query(downloadManagerQuery)

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)

                when (cursor.getInt(columnIndex)) {

                    DownloadManager.STATUS_FAILED -> {
                        downloadStatus = getString(R.string.download_status_failed)
                    }

                    DownloadManager.STATUS_SUCCESSFUL -> {
                        downloadStatus = getString(R.string.download_status_success)
                    }

                    DownloadManager.STATUS_PAUSED -> {
                        downloadStatus = getString(R.string.download_status_paused)
                    }


                }

            }
        }
        return downloadStatus
    }


}
