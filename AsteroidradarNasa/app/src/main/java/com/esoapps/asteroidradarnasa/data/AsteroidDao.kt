package com.esoapps.asteroidradarnasa.data

import androidx.room.*
import com.esoapps.asteroidradarnasa.model.Asteroid
import com.esoapps.asteroidradarnasa.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    // @Query("SELECT * FROM ASTEROID_TABLE ORDER BY ${Constants.CLOSE_APPROACH_DATE}  ASC")
    @Query("SELECT * FROM ${Constants.ASTEROID_TABLE_NAME} ORDER BY ${Constants.CLOSE_APPROACH_DATE}  ASC")
//    fun getAllDbAsteroid(): LiveData<List<Asteroid>>
    fun getAllDbAsteroid(): Flow<List<Asteroid>>


    @Query("SELECT * FROM ${Constants.ASTEROID_TABLE_NAME}  WHERE  ${Constants.CLOSE_APPROACH_DATE} >= :startDate AND  ${Constants.CLOSE_APPROACH_DATE} <= :endDate ORDER BY  ${Constants.CLOSE_APPROACH_DATE} ASC")
    fun getAsteroidsByDurationDates(startDate: String, endDate: String): Flow<List<Asteroid>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(vararg asteroid: Asteroid?)


    @Query("SELECT * from ${Constants.ASTEROID_TABLE_NAME} WHERE ${Constants.CLOSE_APPROACH_DATE} = :date")
    suspend fun getAsteroidByDate(date: String): Asteroid?


    @Query("DELETE FROM ${Constants.ASTEROID_TABLE_NAME} WHERE  ${Constants.CLOSE_APPROACH_DATE}< :date")
    suspend fun deleteLastDayAsteroid(date: String): Int




//NO NEED FOR THEM NOW:
//    @Update
//    suspend fun updateAsteroid(asteroid: Asteroid?)

//    @Delete
//    suspend fun deleteAsteroid(asteroid: Asteroid?)
}