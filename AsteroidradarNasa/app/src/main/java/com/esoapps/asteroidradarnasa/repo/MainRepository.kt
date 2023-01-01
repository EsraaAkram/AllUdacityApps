package com.esoapps.asteroidradarnasa.repo

import android.util.Log
import com.esoapps.asteroidradarnasa.data.AsteroidDao
import com.esoapps.asteroidradarnasa.model.Asteroid
import com.esoapps.asteroidradarnasa.model.PictureOfDay
import com.esoapps.asteroidradarnasa.network.api.Api
import com.esoapps.asteroidradarnasa.network.api.getNextSevenDaysFormattedDates
import com.esoapps.asteroidradarnasa.network.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.coroutines.cancellation.CancellationException


class MainRepository(private val dao: AsteroidDao) {

    //var allDbAsteroids = dao.getAllDbAsteroid()//NO NEED I ALREADY USING getAllDbAsteroid THAT RETURN FLOW THAT I NEED TO COLLECT IN SCOPE

    suspend fun refreshAllAsteroid() {

        withContext(Dispatchers.IO) {
            var week=getNextSevenDaysFormattedDates(7)
            Log.d("refreshAll",week.toString())
            Log.d("refreshAll",week.first().toString())
            Log.d("refreshAll",week.last().toString())

            try {//TO AVOID CRASH WHEN NETWORK IS NOT AVAILABLE
                val asteroidData = Api.retrofitService.getAsteroidFromApi(
                    startDate= week.first(),
                    endDate= week.last()
                ).toString()

                var asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidData))

                Log.d("refreshAll",asteroidList.toTypedArray().toString())

                dao.insertAsteroid(*asteroidList.toTypedArray())

            } catch (ce: CancellationException) {
                //throw ce // Needed for coroutine scope cancellation
                Log.d("refreshAll",ce.toString())

            } catch (e: Exception) {
                // display error

                Log.d("refreshAll",e.toString())

            }



        }
    }

    suspend fun getPictureOfDayFromApi(): PictureOfDay? {
        //GETTING PICTURE OF THE DAY DATA:
        var pictureOfDay = Api.retrofitService.getPictureOfDay()

        //IN CASE I WANT TO CHECK RESPONSE CODE:
        //var pictureOfDayData = Api.retrofitService.getPictureOfDay()
        //var pictureOfDay=pictureOfDayData.body()
        //pictureOfDayMutableLiveData.value = pictureOfDayData
        //return pictureOfDay!!

        return pictureOfDay

    }


    //DATABASE:(ROOM):
    fun getAllDbAsteroid(): Flow<List<Asteroid>> = dao.getAllDbAsteroid()
    fun getAsteroidsByDurationDates(startDate: String, endDate: String): Flow<List<Asteroid>> =
        dao.getAsteroidsByDurationDates(startDate, endDate)

    suspend fun insertAsteroid(asteroid: Asteroid) = dao.insertAsteroid(asteroid)
    suspend fun getAsteroidByDate(date: String): Asteroid? = dao.getAsteroidByDate(date)
    suspend fun deleteLastDayAsteroid(date: String) = dao.deleteLastDayAsteroid(date)




    //WORKING FINE TO JUST GET ASTEROID LIST FROM API WHILE TESTING
//    suspend fun getAllAsteroidFromApi(): ArrayList<Asteroid> {
//        var asteroidData = Api.retrofitService.getAsteroid().toString()
//        var asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidData))
//        return asteroidList
//
//    }


    //getPictureOfDayUsingMoshi()//I USED THIS WHEN I WAS NOT USE MOSHI DIRECTLY IN RETROFIT AND RETURN STRING FROM API
    //WORKING FINE BUT I USED MOSHI DIRECTLY:
//    private suspend fun getPictureOfDayUsingMoshi(): PictureOfDay? {
//        var pictureOfDayData = Api.retrofitService.getPictureOfDay().toString()
//
//        //CONVERT DATA FROM STRING TO JSON TO PASS IT TO PictureOfDay USING MOSHI
//        val moshi: Moshi = Moshi.Builder().build()
//        val jsonAdapter: JsonAdapter<PictureOfDay> = moshi.adapter(PictureOfDay::class.java)
//
//        val pictureOfDay = jsonAdapter.fromJson(pictureOfDayData)
//
//        if (pictureOfDay != null) {
//            Log.d("pictureOfDayR", pictureOfDay.imgUrl)
//
//            //POST VALUE IS BETTER FOR BACKGROUND THREAD:
//            //Cannot invoke setValue on a background thread
//            //pictureOfDayLiveData.value=pictureOfDay
//
//            //pictureOfDayMutableLiveData.postValue(pictureOfDay)
//        }
//        return pictureOfDay
//    }
}