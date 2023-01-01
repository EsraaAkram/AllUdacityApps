/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.loadingprogressapp.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Downloading (
    var title: String? = null,
    var downloadUrl: String? = null,
    var status: String? = null,
) : Parcelable