package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemEventListBinding
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.loadImageFromAssets

class EventAdapter constructor(
    eventDetails: ArrayList<EventDetails>,
    val context: Context
) :
    RecyclerView.Adapter<EventAdapter.EventAdapterViewHolder>() {

    private var eventDetailsList = eventDetails

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapterViewHolder {
        val binding =
            ItemEventListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapterViewHolder, position: Int) {
        holder.bind(eventDetail = eventDetailsList[position], context)
    }

    override fun getItemCount(): Int {
        return eventDetailsList.size
    }

    class EventAdapterViewHolder(private val binding: ItemEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventDetail: EventDetails, context: Context) {
            binding.apply {
                eventNameTextView.text = eventDetail.eventName
                eventDateTextView.text = eventDetail.eventDate
                eventTimeTextView.text = eventDetail.evenTime
                includedTeamsLayout.includedHomeTeam.homeTeamName.text =
                    eventDetail.homeTeam.teamName
                includedTeamsLayout.includedHomeTeam.homeTeamImageView.loadImageFromAssets(
                    eventDetail.homeTeam.teamBadge
                )
                includedTeamsLayout.includedAwayTeam.awayTeam.text = eventDetail.awayTeam.teamName
                includedTeamsLayout.includedAwayTeam.awayTeamImageView.loadImageFromAssets(
                    eventDetail.awayTeam.teamBadge
                )

                bookingDetailsLayout.clickToAction {
                    bookingDetails(eventDetail)
                }
            }
        }
    }

    fun setEventItems(newItems: ArrayList<EventDetails>) {
        eventDetailsList = newItems
        notifyDataSetChanged()
    }
}

var bookingDetails:(EventDetails) -> Unit = {}