package com.esoapps.asteroidradarnasa.utils

object Constants {

    //Asteroid:
    //https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY
    //PictureOfDay:
    //https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY


    const val BASE_URL = "https://api.nasa.gov/"
    //const val NASA_KEY = "DEMO_KEY"//TODO:ADD YOURS
    const val NASA_KEY = "YjNsk1HqpWr41RgWqfbkZJ7CeehlF6YmcoKeLN4Y"//TODO:REMOVE MY KEYS


    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7



    //API JSON OBJECTS:
    //PICTURE OF DAY:
    const val IMG_MEDIA_TYPE="media_type"
    const val TITLE_OF_DAY="title"
    const val IMG_URL="url"

    const val IMG_TYPE_CORRECT="image"



    //ROOM:
    const val ASTEROID_DATABASE_NAME="asteroid_db"
    const val ASTEROID_TABLE_NAME="asteroid_table"

    const val ASTEROID_ID="id"
    const val CODE_NAME="code_name"
    const val CLOSE_APPROACH_DATE="close_approach_date"
    const val ABSOLUTE_MAGNITUDE="absolute_magnitude"
    const val ESTIMATED_DIAMETER="estimated_diameter"
    const val RELATIVE_VELOCITY="relative_velocity"
    const val DISTANCE_FROM_EARTH="distance_from_earth"
    const val IS_POTENTIALLY_HAZARDOUS="is_potentially_hazardous"







}