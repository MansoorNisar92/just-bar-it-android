package com.android.app.justbarit.presentation.feature_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.AmenitiesLayoutBinding
import com.android.app.justbarit.domain.model.Amenity
import com.android.app.justbarit.domain.model.AmenityType
import com.android.app.justbarit.presentation.common.ext.default

class AmenityAdapter constructor(amenities: ArrayList<Amenity>) :
    RecyclerView.Adapter<AmenityAdapter.AmenityViewHolder>() {

    private var amenitiesList = amenities


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenityViewHolder {
        val binding =
            AmenitiesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AmenityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AmenityViewHolder, position: Int) {
        holder.bind(amenity = amenitiesList[position])
    }

    override fun getItemCount(): Int {
        return amenitiesList.size
    }

    class AmenityViewHolder(private val binding: AmenitiesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(amenity: Amenity) {
            binding.apply {
                amenityImageView.getImageBasedOnAmenityType(amenity)
            }
        }
    }

    fun setAmenities(amenities: ArrayList<Amenity>) {
        amenitiesList = amenities
        notifyDataSetChanged()
    }
}

fun AppCompatImageView.getImageBasedOnAmenityType(amenity: Amenity) {
    if (amenity.amenityProvided.default) {
        var resourceId = 0
        when (amenity.amenityType) {
            AmenityType.Disco -> {
                resourceId = R.drawable.dance
            }

            AmenityType.Drinks -> {
                resourceId = R.drawable.copa
            }

            AmenityType.Food -> {
                resourceId = R.drawable.food
            }

            AmenityType.GameNight -> {
                resourceId = R.drawable.game_night
            }

            AmenityType.LiveMusic -> {
                resourceId = R.drawable.game_night
            }

            AmenityType.PetFriendly -> {
                resourceId = R.drawable.pet_friendly
            }

            AmenityType.SmokingArea -> {
                resourceId = R.drawable.smoking
            }

            AmenityType.Sports -> {
                resourceId = R.drawable.game_night
            }

            AmenityType.TerraceRoofTop -> {
                resourceId = R.drawable.outside_area
            }

            AmenityType.Wifi -> {}
        }


        setBackgroundResource(resourceId)
    }
}
