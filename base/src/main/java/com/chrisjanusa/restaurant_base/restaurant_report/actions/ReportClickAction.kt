package com.chrisjanusa.restaurant_base.restaurant_report.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.CommunicationHelper.sendAction
import com.chrisjanusa.base.CommunicationHelper.sendEvent
import com.chrisjanusa.base.CommunicationHelper.sendUpdate
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.restaurant.Restaurant
import com.chrisjanusa.restaurant_base.restaurant_block.updaters.BlockUpdater
import com.chrisjanusa.restaurant_base.restaurant_report.events.ReportEvent
import com.chrisjanusa.restaurant_base.restaurant_report.events.ReportProcessingEvent
import com.chrisjanusa.restaurant_base.restaurant_report.events.ReportStatusEvent
import com.chrisjanusa.restaurant_base.restaurant_report.updaters.ReportedUpdater
import kotlinx.coroutines.channels.Channel


open class ReportClickAction(private val restaurant: Restaurant) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        val reportMap = currentState.value?.reportMap
        if (reportMap?.contains(restaurant.id) == true) {
            val reportId = reportMap[restaurant.id]
            if (reportId.isNullOrBlank()) {
                eventChannel.send(ReportProcessingEvent(restaurant))
            } else {
                eventChannel.send(ReportStatusEvent(restaurant, reportId))
            }
        } else {
            eventChannel.send(ReportEvent(restaurant))
        }
    }
}