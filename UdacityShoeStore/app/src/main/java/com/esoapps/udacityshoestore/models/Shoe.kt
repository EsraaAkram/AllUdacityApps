/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.udacityshoestore.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
//import kotlinx.android.parcel.Parcelize//DEPRECATED
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shoe(

    private var name: String,
    private var description: String,

    private var company: String,
    private var size: Double,

    val images: List<String> = mutableListOf()

) : Parcelable, BaseObservable() {

    @Bindable
    fun getName() : String {
        return name
    }
    fun setName(value : String) {
        name = value
    }

    @Bindable
    fun getCompany() : String {
        return company
    }
    fun setCompany(value : String) {
        company = value
    }

    @Bindable
    fun getDescription() : String {
        return description
    }
    fun setDescription(value : String) {
        description = value
    }

    @Bindable
    fun getSize() : String {
        return size.toString()
    }
    fun setSize(value : String) {
        size = value.toDouble()
    }

}
