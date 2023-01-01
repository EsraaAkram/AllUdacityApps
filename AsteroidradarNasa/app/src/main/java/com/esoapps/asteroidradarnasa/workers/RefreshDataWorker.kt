package com.esoapps.asteroidradarnasa.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.esoapps.asteroidradarnasa.App
import com.esoapps.asteroidradarnasa.repo.MainRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context,
                        params: WorkerParameters
): CoroutineWorker(appContext, params){

    companion object {
        const val REFRESH_ASTEROID_WORK_NAME = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {


        return try {
//        val database = AsteroidDatabase.getAsteroidDatabase(applicationContext)
            val database =App.instance.database
            val repository = MainRepository(database.asteroidDao)

            repository.refreshAllAsteroid()
            Result.success()


        } catch (e: HttpException) {
            Result.retry()
        }


    }


}