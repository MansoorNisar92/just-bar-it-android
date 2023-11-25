package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemEventListV3Binding
import com.android.app.justbarit.domain.model.EventDetails

class HomeEventAdapter constructor(
    eventDetails: ArrayList<EventDetails>,
    val context: Context
) :
    RecyclerView.Adapter<HomeEventAdapter.HomeEventAdapterViewHolder>() {

    private var eventDetailsList = eventDetails

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeEventAdapterViewHolder {
        val binding =
            ItemEventListV3Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeEventAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeEventAdapterViewHolder, position: Int) {
        holder.bind(eventDetail = eventDetailsList[position], context)
    }

    override fun getItemCount(): Int {
        return eventDetailsList.size
    }

    class HomeEventAdapterViewHolder(private val binding: ItemEventListV3Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventDetail: EventDetails, context: Context) {
            binding.apply {
                /*eventNameTextView.text = eventDetail.eventName
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
                }*/
            }
        }
    }

    fun setEventItems(newItems: ArrayList<EventDetails>) {
        eventDetailsList = newItems
        notifyDataSetChanged()
    }
}
