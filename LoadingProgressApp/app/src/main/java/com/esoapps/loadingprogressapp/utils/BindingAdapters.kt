/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.loadingprogressapp.utils

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.esoapps.loadingprogressapp.R

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("statusColor")
fun setStatusColor(textView: TextView, downloadStatus: String,
                     ) {
    if (downloadStatus==textView.context.getString(R.string.download_status_success)) {

        textView.setTextColor(textView.context.getColor(R.color.colorPrimaryDark))


    } else {
        textView.setTextColor(textView.context.getColor(R.color.colorAccent))

    }


}
