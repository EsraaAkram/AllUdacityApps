/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */
package com.esoapps.loadingprogressapp.notifications.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class PermissionHelperNormal(
    var context: Context,

    var currentAct: AppCompatActivity?,

    ) {
    var doneAskingForPermission=false

    fun askNotificationPermission():Boolean{


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    currentAct!!,
                    Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                doneAskingForPermission=getPermissionLauncherResult()



                return doneAskingForPermission//USER CHOICE

            }else{//ALREADY GRANTED BEFORE

                doneAskingForPermission=true
                return doneAskingForPermission
            }


        }else{//BELOW WANTED VERSION SO PERMISSION REQUEST NOT NEEDED ANY WAY
            doneAskingForPermission=true
            return doneAskingForPermission
        }

    }




    private fun getPermissionLauncherResult():Boolean{

        var requestPermissionLauncher = currentAct?.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            doneAskingForPermission=isGranted

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requestPermissionLauncher?.launch(Manifest.permission.POST_NOTIFICATIONS)
        }


        return doneAskingForPermission
    }



}

