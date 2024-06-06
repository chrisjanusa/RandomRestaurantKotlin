package com.chrisjanusa.foursquare.models.report

data class ReportStatus(
    val reportId: String,
    val fsq_id: String,
    val proposed_edit_type: String,
    val created_at: String,
    val resolved_time: String,
    val status: String
)