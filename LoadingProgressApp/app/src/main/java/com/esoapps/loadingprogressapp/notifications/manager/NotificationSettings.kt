/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */
package com.esoapps.loadingprogressapp.notifications.manager



import android.content.Context
import android.content.Intent
import com.esoapps.loadingprogressapp.activities.DetailAct
import com.esoapps.loadingprogressapp.utils.Constants.Companion.DOWNLOAD_FILE_NAME_KEY
import com.esoapps.loadingprogressapp.utils.Constants.Companion.DOWNLOAD_STATUS_KEY

import java.util.*


fun prepareToShowNotification(
    context: Context,
    title: String,
    bodyDesc: String,
    downloadStatus: String,
    downloadFileName: String,
) {
    createNotificationsChannel(context)

    var notificationIntent = Intent(context, DetailAct::class.java)

    //NOTIFICATION DATA:
    notificationIntent.putExtra(DOWNLOAD_FILE_NAME_KEY, downloadFileName)
    notificationIntent.putExtra(DOWNLOAD_STATUS_KEY, downloadStatus)

    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

    val contentPendingIntent = getContentPendingIntentType(notificationIntent,context)

    showNotificationOnScreen(title,bodyDesc,contentPendingIntent,context)


}



