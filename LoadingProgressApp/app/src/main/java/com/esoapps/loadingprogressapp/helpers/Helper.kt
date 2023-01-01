/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.loadingprogressapp.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.Toast
import com.esoapps.loadingprogressapp.R


fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}


fun checkValidUrl(context: Context?,textUrl:String?):Boolean{

    if (textUrl!=null){

        if (textUrl!=""){
            //CHECK IF URL IS HTTPS BECAUSE DOWNLOAD MANAGER ONLY WORKS WITH HTTPS
            if (URLUtil.isValidUrl(textUrl)){

                return true
            }else{
                //INFORM USER TO ENTER URL:
                Toast.makeText(
                    context,
                    context?.getString(R.string.enter_valid_url_toast_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            //INFORM USER TO ENTER URL:
            Toast.makeText(
                context,
                context?.getString(R.string.enter_url_toast_msg),
                Toast.LENGTH_SHORT
            ).show()
        }
    }else{
        //INFORM USER TO ENTER URL:
        Toast.makeText(
            context,
            context?.getString(R.string.enter_url_toast_msg),
            Toast.LENGTH_SHORT
        ).show()
    }


    return false
}