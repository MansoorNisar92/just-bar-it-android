package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemHomeEventBinding
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.loadImageFromAssets

class HomeEventAdapter constructor(
    eventDetails: ArrayList<EventDetails>
) :
    RecyclerView.Adapter<HomeEventAdapter.HomeEventAdapterViewHolder>() {

    private var eventDetailsList = eventDetails

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeEventAdapterViewHolder {
        val binding =
            ItemHomeEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeEventAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeEventAdapterViewHolder, position: Int) {
        holder.bind(eventDetail = eventDetailsList[position])
    }

    override fun getItemCount(): Int {
        return eventDetailsList.size
    }

    class HomeEventAdapterViewHolder(private val binding: ItemHomeEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventDetail: EventDetails) {
            binding.apply {
                eventImageView.loadImageFromAssets(eventDetail.eventImage)
                evenTitleTextView.text = eventDetail.title
                eventTime.text = eventDetail.date
                eventHostedByNameTextView.text = eventDetail.location
                ticketStartsFromTextView.text = eventDetail.price
                eventCardView.clickToAction {
                    homeEventClick(eventDetail)
                }
            }
        }
    }

    fun setEventItems(newItems: ArrayList<EventDetails>) {
        eventDetailsList = newItems
        notifyDataSetChanged()
    }
}

var homeEventClick:(EventDetails) -> Unit  = {}
