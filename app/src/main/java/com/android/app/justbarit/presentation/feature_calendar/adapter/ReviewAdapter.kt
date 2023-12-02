package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemReviewBinding
import com.android.app.justbarit.domain.model.Review

class ReviewAdapter constructor(
    reviews: ArrayList<Review>
) :
    RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>() {

    private var reviewsList = reviews

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapterViewHolder {
        val binding =
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewAdapterViewHolder, position: Int) {
        holder.bind(review = reviewsList[position])
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    class ReviewAdapterViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.apply {
                reviewerNameTextView.text = review.reviewerName
                reviewTextView.text = review.review
                reviewerRatingBar.rating = review.rating
            }
        }
    }

    fun setReviews(newItems: ArrayList<Review>) {
        reviewsList = newItems
        notifyDataSetChanged()
    }
}