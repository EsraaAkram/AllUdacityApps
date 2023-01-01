package com.esoapps.asteroidradarnasa.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esoapps.asteroidradarnasa.model.Asteroid
import com.esoapps.asteroidradarnasa.model.PictureOfDay
import com.esoapps.asteroidradarnasa.network.api.getNextSevenDaysFormattedDates
import com.esoapps.asteroidradarnasa.repo.MainRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    //API:
    private var _pictureOfDay = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>? get() = _pictureOfDay


    //DATABASE:
    private var _allDbAsteroid = MutableLiveData<List<Asteroid>?>(mutableListOf())
    val allDbAsteroid: LiveData<List<Asteroid>?> get() = _allDbAsteroid

//    private var _allDbAsteroidByDurationDates = MutableLiveData<List<Asteroid>?>(mutableListOf())
//    val allDbAsteroidByDurationDates: LiveData<List<Asteroid>?> get() = _allDbAsteroidByDurationDates
//
//    private var _dbAsteroidByTodayDate = MutableLiveData<Asteroid?>()
//    val dbAsteroidByDate: LiveData<Asteroid?> get() = _dbAsteroidByTodayDate


    init {

        getPictureOfDayFromApi()

        getAllAsteroidFromApiThenAddToDb()
        getAllDbAsteroid()//GET WANTED ASTRO LIST BY MENU ITEM USING LIVE DATA IN REPO
    }


    private fun getPictureOfDayFromApi() {

        viewModelScope.launch {

            try {//TO AVOID CRASH WHEN NETWORK IS NOT AVAILABLE
                var pictureOfDay=mainRepository.getPictureOfDayFromApi()
                if (pictureOfDay!=null){
                    _pictureOfDay.value=pictureOfDay
                }
            } catch (ce: CancellationException) {
                throw ce // Needed for coroutine scope cancellation
            } catch (e: Exception) {
                // display error
            }




        }
    }


    private fun getAllAsteroidFromApiThenAddToDb() {

        viewModelScope.launch {
            mainRepository.refreshAllAsteroid()

        }

    }



    //DATABASE:

    fun getAllDbAsteroid() {
        viewModelScope.launch {
            mainRepository.getAllDbAsteroid()
                .collect {
                    _allDbAsteroid.value = it


                }
        }
    }





    fun getAllDbAsteroidByDurationDates() {
        viewModelScope.launch {
            mainRepository.getAsteroidsByDurationDates(
                getNextSevenDaysFormattedDates(1).first(),
                getNextSevenDaysFormattedDates(7).last(),
            )
                .collect {
                    //_allDbAsteroidByDurationDates.value = it

                    _allDbAsteroid.value=it
                }
        }
    }


    fun getDbAsteroidByDate() {//JUST TODAY
        viewModelScope.launch {
//            _dbAsteroidByTodayDate.value = mainRepository.getAsteroidByDate(
//                getNextSevenDaysFormattedDates(1).first(),//RETURN TODAY DATE
//            )
            mainRepository.getAsteroidsByDurationDates(
                getNextSevenDaysFormattedDates(1).first(),
                getNextSevenDaysFormattedDates(1).last(),
            )
                .collect {
                    //_allDbAsteroidByDurationDates.value = it

                    _allDbAsteroid.value=it
                }

        }
    }






    //NO NEED IN THIS PROJECT BUT I WROTE THEM ANYWAY:

    fun insertAsteroid(asteroid: Asteroid){
        viewModelScope.launch {
            mainRepository.insertAsteroid(asteroid)
        }
    }



    fun deleteLastDayAsteroid() {

        viewModelScope.launch {

            mainRepository.deleteLastDayAsteroid(
                getNextSevenDaysFormattedDates(1).first(),
            )
        }
    }



//    fun getAllAsteroidFromApi() {
//
//        viewModelScope.launch {
//
//
//            _asteroidMutableLiveDataList.value=mainRepository.getAllAsteroidFromApi()
//            mainRepository.refreshAllAsteroid()
//
//
//            Log.d("_asteroidMutableLiveDataList", _asteroidMutableLiveDataList.value.toString())
//
//
//        }
//    }
}

