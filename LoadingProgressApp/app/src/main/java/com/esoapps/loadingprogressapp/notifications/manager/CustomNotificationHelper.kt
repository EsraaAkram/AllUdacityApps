/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.loadingprogressapp.notifications.manager


import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


@RequiresApi(Build.VERSION_CODES.N)
fun getCurrentNotificationsPriorityAbove26(codeOfNotificationsPriority: Int): Int {

    when (codeOfNotificationsPriority) {
        0 -> {
            return NotificationManager.IMPORTANCE_LOW
        }
        1 -> {
            return NotificationManager.IMPORTANCE_DEFAULT
        }
        2 -> {
            return NotificationManager.IMPORTANCE_HIGH
        }
        else -> return NotificationManager.IMPORTANCE_DEFAULT
    }
}

fun getCurrentNotificationsPriorityBelow26(codeOfNotificationsPriority: Int): Int {

    when (codeOfNotificationsPriority) {
        0 -> {
            return NotificationCompat.PRIORITY_LOW
        }
        1 -> {
            return NotificationCompat.PRIORITY_DEFAULT
        }
        2 -> {
            return NotificationCompat.PRIORITY_HIGH
        }
        else -> return NotificationCompat.PRIORITY_DEFAULT
    }
}

