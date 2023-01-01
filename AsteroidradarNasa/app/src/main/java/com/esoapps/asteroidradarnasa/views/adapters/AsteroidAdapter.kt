package com.esoapps.asteroidradarnasa.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esoapps.asteroidradarnasa.databinding.AsteroidItemRowBinding
import com.esoapps.asteroidradarnasa.model.Asteroid

class AsteroidAdapter(private val asteroidClickListener: AsteroidClickListener) :  ListAdapter<Asteroid,
        RecyclerView.ViewHolder>(AsteroidDiffUtil()) {

    //RECYCLER VIEW METHODS:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val asteroidItem = getItem(position)
        holder as AsteroidViewHolder
        holder.bind(asteroidItem, asteroidClickListener)
    }


    class AsteroidViewHolder private constructor(private val binding: AsteroidItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid, asteroidClickListener: AsteroidClickListener) {
            binding.asteroid = asteroid
            binding.asteroidClickListener = asteroidClickListener

            //REFRESH SCREEN BINDING:
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemRowBinding.inflate(layoutInflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }
    }

    class AsteroidDiffUtil : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldAsteroid: Asteroid, newAsteroid: Asteroid): Boolean {
            return oldAsteroid.id == newAsteroid.id
        }

        override fun areContentsTheSame(oldAsteroid: Asteroid, newAsteroid: Asteroid): Boolean {
            return oldAsteroid == newAsteroid
        }
    }

    class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }


}