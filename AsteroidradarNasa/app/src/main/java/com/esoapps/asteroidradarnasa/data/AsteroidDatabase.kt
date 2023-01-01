package com.esoapps.asteroidradarnasa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esoapps.asteroidradarnasa.model.Asteroid
import com.esoapps.asteroidradarnasa.utils.Constants.ASTEROID_DATABASE_NAME


@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao

    companion object {

        @Volatile //ALWAYS UP-TO-DATE
        private var INSTANCE: AsteroidDatabase? = null


        fun getAsteroidDatabase(context: Context): AsteroidDatabase {

            synchronized(this) {
                var instance= INSTANCE

                if (instance==null){

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        ASTEROID_DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE= instance
                }

                return instance
            }


        }


    }


}