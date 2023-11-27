package com.android.app.justbarit.presentation.feature_search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ItemBarsBinding
import com.android.app.justbarit.domain.model.AmenityType
import com.android.app.justbarit.domain.model.Bar
import com.android.app.justbarit.presentation.common.Constants
import com.android.app.justbarit.presentation.common.ext.default
import com.android.app.justbarit.presentation.common.ext.getRatingInDesc
import com.android.app.justbarit.presentation.common.ext.loadImage
import com.android.app.justbarit.presentation.common.ext.loadImageFromAssets
import kotlin.math.roundToInt

class BarAdapter constructor(bars: ArrayList<Bar>) :
    RecyclerView.Adapter<BarAdapter.BarViewHolder>() {

    private var barsList = bars
    private var filteredItemList = barsList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarViewHolder {
        val binding =
            ItemBarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BarViewHolder, position: Int) {
        holder.bind(bar = filteredItemList[position])
    }

    override fun getItemCount(): Int {
        return filteredItemList.size
    }

    class BarViewHolder(private val binding: ItemBarsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bar: Bar) {
            binding.apply {
                if (bar.imageId!=null){
                    barCoverImageView.loadImage("${Constants.imageBaseUrl}${bar.imageId}")
                }else{
                    barCoverImageView.loadImageFromAssets(R.drawable.ic_default_image_placeholder)
                }
                barNameTextView.text = bar.barName
                barRatingTextView.text = bar.barRating.toString()

                val ratingForDesc = bar.barRating.roundToInt()
                barRatingDescTextView.text = getRatingInDesc(ratingForDesc)

                val review = if (bar.reviewCount > 0) {
                    "(${bar.reviewCount})"
                } else {
                    "(No Reviews)"
                }
                barReviewCountTextView.text = review

                var distance = "Bar Restaurant - {DIS} from city center"
                distance = distance.replace("{DIS}", bar.distance.toString())
                barDistanceTextView.text = distance

                val hasFreeEntry = if (bar.hasFreeEntry) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                freeEntryTextView.visibility = hasFreeEntry
                val amenityImageViewMap = mapOf(
                    AmenityType.Wifi to includedAmenities.wifiImageView,
                    AmenityType.Disco to includedAmenities.danceImageView,
                    AmenityType.Drinks to includedAmenities.drinksImageView,
                    AmenityType.Food to includedAmenities.foodImageView,
                    AmenityType.GameNight to includedAmenities.gameNightImageView,
                    AmenityType.LiveMusic to includedAmenities.liveMusicImageView,
                    AmenityType.PetFriendly to includedAmenities.petFriendlyImageView,
                    AmenityType.SmokingArea to includedAmenities.smokingImageView,
                    AmenityType.Sports to includedAmenities.sportsImageView,
                    AmenityType.TerraceRoofTop to includedAmenities.outsideAreaImageView
                )

                bar.amenities.forEach { amenity ->
                    val imageView = amenityImageViewMap[amenity.amenityType]
                    imageView?.visibility = if (amenity.amenityProvided.default) View.VISIBLE else View.GONE
                }
            }
        }
    }

    fun setBars(newBars: ArrayList<Bar>) {
        barsList = newBars
        filteredItemList = newBars
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredItemList = if (query.isEmpty()) {
            barsList
        } else {
            barsList.filter { it.barName.contains(query, ignoreCase = true) } as ArrayList<Bar>
        }
        notifyDataSetChanged()
    }

}

var barClick: (Bar) -> Unit = {

}

private fun hasAmenity(hasAmenity: Boolean): Float {
    val alpha = when {
        hasAmenity -> {
            1.0f
        }

        else -> {
            0.4f
        }
    }
    return alpha
}