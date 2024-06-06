package com.chrisjanusa.randomizer.foursquare.events

import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseRestaurantFragment
import com.google.android.play.core.review.ReviewManagerFactory

class ReviewRequestEvent : BaseEvent {
    override fun handleEvent(fragment: BaseRestaurantFragment) {
        fragment.context?.let { context ->
            val manager = ReviewManagerFactory.create(context)
            val requestReview = manager.requestReviewFlow()
            requestReview.addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    val reviewInfo = request.result
                    fragment.activity?.let { manager.launchReviewFlow(it, reviewInfo) }
                }
            }
        }
    }
}
