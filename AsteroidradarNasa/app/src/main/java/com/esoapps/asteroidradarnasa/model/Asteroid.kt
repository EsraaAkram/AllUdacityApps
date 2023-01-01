package com.esoapps.asteroidradarnasa.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esoapps.asteroidradarnasa.utils.Constants
import com.esoapps.asteroidradarnasa.utils.Constants.ABSOLUTE_MAGNITUDE
import com.esoapps.asteroidradarnasa.utils.Constants.ASTEROID_ID
import com.esoapps.asteroidradarnasa.utils.Constants.ASTEROID_TABLE_NAME
import com.esoapps.asteroidradarnasa.utils.Constants.CLOSE_APPROACH_DATE
import com.esoapps.asteroidradarnasa.utils.Constants.CODE_NAME
import com.esoapps.asteroidradarnasa.utils.Constants.DISTANCE_FROM_EARTH
import com.esoapps.asteroidradarnasa.utils.Constants.ESTIMATED_DIAMETER
import com.esoapps.asteroidradarnasa.utils.Constants.IS_POTENTIALLY_HAZARDOUS
import com.esoapps.asteroidradarnasa.utils.Constants.RELATIVE_VELOCITY


@Entity(tableName = ASTEROID_TABLE_NAME)
@Parcelize
data class Asteroid(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ASTEROID_ID) val id: Long,

    @ColumnInfo(name = CODE_NAME) val codeName: String,
    @ColumnInfo(name = CLOSE_APPROACH_DATE) val closeApproachDate: String,
    @ColumnInfo(name = ABSOLUTE_MAGNITUDE) val absoluteMagnitude: Double,
    @ColumnInfo(name = ESTIMATED_DIAMETER) val estimatedDiameter: Double,
    @ColumnInfo(name = RELATIVE_VELOCITY) val relativeVelocity: Double,
    @ColumnInfo(name = DISTANCE_FROM_EARTH) val distanceFromEarth: Double,
    @ColumnInfo(name =IS_POTENTIALLY_HAZARDOUS) val isPotentiallyHazardous: Boolean

) : Parcelable{

}


//@Parcelize
//data class Asteroid(
//    val id: Long,
//
//    val codeName: String,
//    val closeApproachDate: String,
//    val absoluteMagnitude: Double,
//    val estimatedDiameter: Double,
//    val relativeVelocity: Double,
//    val distanceFromEarth: Double,
//    val isPotentiallyHazardous: Boolean
//
//) : Parcelable {
//
//}
