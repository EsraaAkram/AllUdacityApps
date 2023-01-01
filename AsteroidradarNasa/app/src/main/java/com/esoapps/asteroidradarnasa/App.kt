package com.esoapps.asteroidradarnasa

import android.app.Application
import androidx.work.*
import com.esoapps.asteroidradarnasa.data.AsteroidDatabase
import com.esoapps.asteroidradarnasa.repo.MainRepository
import com.esoapps.asteroidradarnasa.workers.DeleteDataWorker
import com.esoapps.asteroidradarnasa.workers.RefreshDataWorker
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class App : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    //ACCORDING TO GOOGLE CODE LAB, USING SINGLE SOURCE OF DATABASE AND DAO:
    val database by lazy { AsteroidDatabase.getAsteroidDatabase(this) }
    val repository by lazy { MainRepository(database.asteroidDao) }


    override fun onCreate() {
        super.onCreate()

        instance = this

        delayedInit()
        //cachePicasso()
    }


    companion object {
        lateinit var instance: App
            private set
    }

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//I ALREADY USED HIGHER SDK
                setRequiresDeviceIdle(true)
                //}
            }.build()


        //REFRESH DATA:
        val refreshRepeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        //WorkManager.getInstance() Deprecated
        WorkManager.getInstance(instance).enqueueUniquePeriodicWork(
            RefreshDataWorker.REFRESH_ASTEROID_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshRepeatingRequest
        )

        //DELETE DATA:
        val deleteRepeatingRequest = PeriodicWorkRequestBuilder<DeleteDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(instance).enqueueUniquePeriodicWork(
            DeleteDataWorker.DELETE_ASTEROID_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            deleteRepeatingRequest
        )



    }


}