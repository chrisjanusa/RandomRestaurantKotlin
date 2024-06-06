package com.chrisjanusa.foursquare.models.report

data class ReportResults(
    val proposed_edits: List<ReportStatus> = ArrayList()
)