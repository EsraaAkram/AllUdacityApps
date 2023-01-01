package com.esoapps.asteroidradarnasa.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esoapps.asteroidradarnasa.utils.Constants

@Entity(tableName = Constants.ASTEROID_TABLE_NAME)
class AsteroidDb constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.ASTEROID_ID) val id: Long,

    @ColumnInfo(name = Constants.CODE_NAME) val codeName: String,
    @ColumnInfo(name = Constants.CLOSE_APPROACH_DATE) val closeApproachDate: String,
    @ColumnInfo(name = Constants.ABSOLUTE_MAGNITUDE) val absoluteMagnitude: Double,
    @ColumnInfo(name = Constants.ESTIMATED_DIAMETER) val estimatedDiameter: Double,
    @ColumnInfo(name = Constants.RELATIVE_VELOCITY) val relativeVelocity: Double,
    @ColumnInfo(name = Constants.DISTANCE_FROM_EARTH) val distanceFromEarth: Double,
    @ColumnInfo(name = Constants.IS_POTENTIALLY_HAZARDOUS) val isPotentiallyHazardous: Boolean
) {
}