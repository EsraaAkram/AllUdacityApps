package com.esoapps.asteroidradarnasa.model


import com.esoapps.asteroidradarnasa.utils.Constants.IMG_MEDIA_TYPE
import com.esoapps.asteroidradarnasa.utils.Constants.IMG_URL
import com.esoapps.asteroidradarnasa.utils.Constants.TITLE_OF_DAY
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PictureOfDay(
    @Json(name =IMG_MEDIA_TYPE)
    val mediaType: String,

    @Json(name =TITLE_OF_DAY)
    val titleOfDay: String,

    @Json(name =IMG_URL)
    val imgUrl: String
)