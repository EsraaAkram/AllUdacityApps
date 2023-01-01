package com.esoapps.asteroidradarnasa.network.converters

import com.esoapps.asteroidradarnasa.model.Asteroid
import com.esoapps.asteroidradarnasa.model.AsteroidDb

//fun List<AsteroidDb>.asDomainModel(): List<Asteroid> {
fun List<AsteroidDb>.asDomainModel(): List<Asteroid> {
    return map {asteroidDb->
        Asteroid (
            id = asteroidDb.id,
            codeName = asteroidDb.codeName,
            closeApproachDate = asteroidDb.closeApproachDate,
            absoluteMagnitude = asteroidDb.absoluteMagnitude,
            estimatedDiameter = asteroidDb.estimatedDiameter,
            relativeVelocity = asteroidDb.relativeVelocity,
            distanceFromEarth = asteroidDb.distanceFromEarth,
            isPotentiallyHazardous = asteroidDb.isPotentiallyHazardous
        )
    }
}



fun List<AsteroidDb>.asDatabaseModel(): Array<AsteroidDb> {
    return map {asteroidDb->
        AsteroidDb(
            id = asteroidDb.id,
            codeName = asteroidDb.codeName,
            closeApproachDate = asteroidDb.closeApproachDate,
            absoluteMagnitude = asteroidDb.absoluteMagnitude,
            estimatedDiameter = asteroidDb.estimatedDiameter,
            relativeVelocity = asteroidDb.relativeVelocity,
            distanceFromEarth = asteroidDb.distanceFromEarth,
            isPotentiallyHazardous = asteroidDb.isPotentiallyHazardous
        )
    }.toTypedArray()
}