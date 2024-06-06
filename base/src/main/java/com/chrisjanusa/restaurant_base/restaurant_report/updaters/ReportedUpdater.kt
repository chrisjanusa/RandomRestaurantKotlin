package com.chrisjanusa.restaurant_base.restaurant_report.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.base.models.ReportId
import com.chrisjanusa.restaurant.Restaurant
import com.chrisjanusa.restaurant.RestaurantId
import java.util.*

class ReportedUpdater(private val reportId: ReportId, private val restaurantId: RestaurantId) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val reportMap = prevState.reportMap.toMutableMap()
        reportMap[restaurantId] = reportId
        return prevState.copy(reportMap = reportMap)
    }
}