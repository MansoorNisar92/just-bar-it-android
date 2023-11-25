package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemEventListV2Binding
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
            ItemEventListV2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapterViewHolder, position: Int) {
        holder.bind(eventDetail = eventDetailsList[position], context)
    }

    override fun getItemCount(): Int {
        return eventDetailsList.size
    }

    class EventAdapterViewHolder(private val binding: ItemEventListV2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventDetail: EventDetails, context: Context) {
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