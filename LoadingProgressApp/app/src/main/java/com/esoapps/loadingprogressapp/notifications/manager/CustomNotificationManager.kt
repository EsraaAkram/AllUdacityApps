/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */
package com.esoapps.loadingprogressapp.notifications.manager

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.esoapps.loadingprogressapp.R
import com.esoapps.loadingprogressapp.utils.Constants.Companion.NOTIFICATION_CHANNEL
import com.esoapps.loadingprogressapp.utils.Constants.Companion.NOTIFICATION_ID
import java.util.*


fun createNotificationsChannel(context: Context) {

    //API ABOVE 26 REQUIRE TO CREATE NOTIFICATION CHANNEL USING NotificationChannel CLASS:
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){


        var channel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            NOTIFICATION_CHANNEL,
            getCurrentNotificationsPriorityAbove26(2))//IMPORTANCE_HIGH

            .apply {
                setShowBadge(true)
            }

        channel.enableLights(true)
        channel.enableVibration(true)

        channel.lightColor = Color.RED

        channel.description = context.getString(R.string.default_notification_desc)

        var manager =ContextCompat.getSystemService(
            context,
            NotificationManager::class.java) as NotificationManager


        manager.createNotificationChannel(channel)

    }

}




fun showNotificationOnScreen(
    title: String,
    bodyDesc: String,
    contentPendingIntent: PendingIntent,
    context: Context
) {

    val eggImage = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.download_img
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(eggImage)
        .bigLargeIcon(null)


    var builder = NotificationCompat
        .Builder(context, NOTIFICATION_CHANNEL)
        .setContentTitle("$title $bodyDesc")

        .setSmallIcon(R.drawable.ic_assistant_black_24dp)//MONO://SO WILL WORK FINE FOR ALL BUILD SDK

        .setColor(ContextCompat.getColor(context, R.color.c_light))
        .setColorized(true)
        .setContentText(bodyDesc)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(getCurrentNotificationsPriorityBelow26(2))
        .setOnlyAlertOnce(false)//true IN CASE YOU WANT ONE NOTIFICATION TO TRIGGER ALARM

        .setStyle(bigPicStyle)
        .setLargeIcon(eggImage)

        .addAction(
            R.drawable.ic_notification,
            context.getString(R.string.notification_button),
            contentPendingIntent
        )


    var manager = NotificationManagerCompat.from(context)



    manager.apply {
        notify(NOTIFICATION_ID, builder.build())
    }

}


fun getContentPendingIntentType(
    notificationIntent: Intent,
    context: Context
): PendingIntent {

    //PENDING INTENT FLAG IS DIFFERENT (SDK S, SDK M, OTHERS SDK)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        getPendingIntent(

            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
            notificationIntent
            ,context)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        getPendingIntent(
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            notificationIntent
            ,context)

    } else {

        getPendingIntent(PendingIntent.FLAG_UPDATE_CURRENT,
            notificationIntent,
            context)

    }
}

private fun getPendingIntent(flag: Int, notificationIntent: Intent, context: Context): PendingIntent
        = PendingIntent.getActivity(context, 0, notificationIntent, flag)


