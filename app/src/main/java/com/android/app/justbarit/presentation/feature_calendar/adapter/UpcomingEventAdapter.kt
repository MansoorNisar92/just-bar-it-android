package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemEventCalendarDetailsBinding
import com.android.app.justbarit.databinding.ItemUpComingEventBinding
import com.android.app.justbarit.domain.model.EventDetails
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.default
import com.android.app.justbarit.presentation.common.ext.loadImageFromAssets

class UpcomingEventAdapter constructor(
    eventDetails: ArrayList<EventDetails>,
    val context: Context
) :
    RecyclerView.Adapter<UpcomingEventAdapter.UpComingEventAdapterViewHolder>() {

    private var eventDetailsList = eventDetails

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpComingEventAdapterViewHolder {
        val binding =
            ItemUpComingEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UpComingEventAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpComingEventAdapterViewHolder, position: Int) {
        holder.bind(eventDetail = eventDetailsList[position], context)
    }

    override fun getItemCount(): Int {
        return eventDetailsList.size
    }

    class UpComingEventAdapterViewHolder(private val binding: ItemUpComingEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventDetail: EventDetails, context: Context) {
            binding.apply {
                eventImageView.loadImageFromAssets(eventDetail.eventImage)
                evenTitleTextView.text = eventDetail.title
                eventTime.text = eventDetail.date
                eventHostedByNameTextView.text = eventDetail.location
                ticketStartsFromTextView.text = eventDetail.price
                if (eventDetail.canChat.default) {
                    chatLayout.visibility = View.VISIBLE
                } else {
                    chatLayout.visibility = View.INVISIBLE
                }

                if (eventDetail.going != null) {
                    goingTextView.visibility = View.VISIBLE
                } else {
                    goingTextView.visibility = View.INVISIBLE
                }
                eventDetailsCardView.clickToAction {
                    upComingEventDetails(eventDetail)
                }
            }
        }
    }

    fun setEventItems(newItems: ArrayList<EventDetails>) {
        eventDetailsList = newItems
        notifyDataSetChanged()
    }
}

var upComingEventDetails: (EventDetails) -> Unit = {}