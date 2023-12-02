package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemEventCalendarDetailsBinding
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.loadImageFromAssets

class EventAdapter constructor(
    eventDetails: ArrayList<EventDetails>
) :
    RecyclerView.Adapter<EventAdapter.EventAdapterViewHolder>() {

    private var eventDetailsList = eventDetails

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapterViewHolder {
        val binding =
            ItemEventCalendarDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapterViewHolder, position: Int) {
        holder.bind(eventDetail = eventDetailsList[position])
    }

    override fun getItemCount(): Int {
        return eventDetailsList.size
    }

    class EventAdapterViewHolder(private val binding: ItemEventCalendarDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventDetail: EventDetails) {
            binding.apply {
                eventImageView.loadImageFromAssets(eventDetail.eventImage)
                evenTitleTextView.text = eventDetail.title
                eventTime.text = eventDetail.date
                eventHostedByNameTextView.text = eventDetail.location
                ticketStartsFromTextView.text = eventDetail.price
                eventDetailsCardView.clickToAction {
                    evetDetails(eventDetail)
                }
            }
        }
    }

    fun setEventItems(newItems: ArrayList<EventDetails>) {
        eventDetailsList = newItems
        notifyDataSetChanged()
    }
}

var evetDetails: (EventDetails) -> Unit = {}