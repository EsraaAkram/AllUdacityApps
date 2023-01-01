package com.esoapps.asteroidradarnasa.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.esoapps.asteroidradarnasa.App
import com.esoapps.asteroidradarnasa.data.AsteroidDatabase
import com.esoapps.asteroidradarnasa.network.api.Api
import com.esoapps.asteroidradarnasa.network.api.getNextSevenDaysFormattedDates
import com.esoapps.asteroidradarnasa.network.api.getTodayDate
import com.esoapps.asteroidradarnasa.network.api.parseAsteroidsJsonResult
import com.esoapps.asteroidradarnasa.repo.MainRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException


class DeleteDataWorker (appContext: Context,
params: WorkerParameters
): CoroutineWorker(appContext, params){

    companion object {
        const val DELETE_ASTEROID_WORK_NAME = "DeleteDataWorker"
    }
    override suspend fun doWork(): Result {


          return try {
              val database =App.instance.database
              val repository = MainRepository(database.asteroidDao)

                repository.deleteLastDayAsteroid(
                    getNextSevenDaysFormattedDates(
                    1).first())
                Result.success()
            } catch (e: Exception) {

                Result.retry()
            }


    }

}