package com.esoapps.asteroidradarnasa.utils


import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.esoapps.asteroidradarnasa.R
import com.squareup.picasso.Picasso


//PICTURE OF DAY BINDING:
@BindingAdapter("imageUrl","mediaType","titleOfImg")
fun setImageUrl(imageView: ImageView, todayImgUrl: String?, mediaType:String?, titleOfImg:String?) {

    imageView.contentDescription=String.format(
        imageView.context.getString(
            R.string.nasa_picture_of_day_content_description_format),
        titleOfImg
    )

    Log.d("todayImgUrl",todayImgUrl.toString())


    //CHECK IF MEDIA TYPE IS A REAL IMAGE NOT VIDEO:
    if (mediaType== Constants.IMG_TYPE_CORRECT){
        Picasso.get()
            .load(todayImgUrl)
            //.networkPolicy(NetworkPolicy.OFFLINE)
            .error(R.drawable.asteroid_safe)
            .into(imageView)


    }

}




@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription=imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {

        imageView.contentDescription=imageView.context.getString(R.string.not_hazardous_asteroid_image)

        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription=imageView.context.getString(R.string.potentially_hazardous_asteroid_image)

        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.contentDescription=imageView.context.getString(R.string.not_hazardous_asteroid_image)

        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}


