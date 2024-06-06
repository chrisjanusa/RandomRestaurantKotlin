package com.chrisjanusa.restaurant_base.restaurant_report.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.restaurant.RestaurantId

class ReportFailedUpdater(private val restaurantId: RestaurantId) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        val reportMap = prevState.reportMap.toMutableMap()
        reportMap.remove(restaurantId)
        return prevState.copy(reportMap = reportMap)
    }
}