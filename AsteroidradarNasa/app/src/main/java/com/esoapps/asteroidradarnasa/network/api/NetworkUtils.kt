package com.esoapps.asteroidradarnasa.network.api

import android.util.Log
import com.esoapps.asteroidradarnasa.model.Asteroid
import com.esoapps.asteroidradarnasa.utils.Constants
import com.esoapps.asteroidradarnasa.utils.Constants.DEFAULT_END_DATE_DAYS
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<Asteroid> {

    val asteroidList = ArrayList<Asteroid>()

        val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")


        val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()


        for (formattedDate in nextSevenDaysFormattedDates) {
            if (nearEarthObjectsJson.has(formattedDate)) {
                val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)


                for (i in 0 until dateAsteroidJsonArray.length()) {
                    val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
                    val id = asteroidJson.getLong("id")
                    val codeName = asteroidJson.getString("name")
                    val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
                    val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                        .getJSONObject("kilometers").getDouble("estimated_diameter_max")

                    val closeApproachData = asteroidJson
                        .getJSONArray("close_approach_data").getJSONObject(0)
                    val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                        .getDouble("kilometers_per_second")
                    val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                        .getDouble("astronomical")
                    val isPotentiallyHazardous = asteroidJson
                        .getBoolean("is_potentially_hazardous_asteroid")


                    val asteroid = Asteroid(
                        id,
                        codeName,
                        formattedDate,
                        absoluteMagnitude,
                        estimatedDiameter,
                        relativeVelocity,
                        distanceFromEarth,
                        isPotentiallyHazardous
                    )

                    asteroidList.add(asteroid)
                }
            }
        }



    return asteroidList
}

fun getNextSevenDaysFormattedDates(numberOfWantedDays:Int=DEFAULT_END_DATE_DAYS): ArrayList<String> {
    val formattedDateList = ArrayList<String>()



    val calendar = Calendar.getInstance()
//    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
    for (i in 0 until numberOfWantedDays) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    Log.d("formattedDateList", formattedDateList.size.toString())
    Log.d("formattedDateList", formattedDateList.toString())

    return formattedDateList
}

fun getTodayDate(){
    val date = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(Date())
}

