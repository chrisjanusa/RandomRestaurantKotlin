package com.chrisjanusa.restaurant_base.restaurant_report.actions

import androidx.lifecycle.LiveData
import com.chrisjanusa.base.CommunicationHelper.sendEvent
import com.chrisjanusa.base.CommunicationHelper.sendUpdate
import com.chrisjanusa.base.interfaces.BaseAction
import com.chrisjanusa.base.interfaces.BaseEvent
import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.MapEvent
import com.chrisjanusa.base.models.RandomizerState
import com.chrisjanusa.foursquare.FourSquareRepository
import com.chrisjanusa.restaurant.Restaurant
import com.chrisjanusa.restaurant_base.restaurant_block.updaters.BlockUpdater
import com.chrisjanusa.restaurant_base.restaurant_report.events.ReportEvent
import com.chrisjanusa.restaurant_base.restaurant_report.events.ReportProcessingEvent
import com.chrisjanusa.restaurant_base.restaurant_report.events.ReportStatusEvent
import com.chrisjanusa.restaurant_base.restaurant_report.updaters.ReportFailedUpdater
import com.chrisjanusa.restaurant_base.restaurant_report.updaters.ReportedUpdater
import kotlinx.coroutines.channels.Channel

open class ReportConfirmAction(private val restaurant: Restaurant) : BaseAction {
    override suspend fun performAction(
        currentState: LiveData<RandomizerState>,
        updateChannel: Channel<BaseUpdater>,
        eventChannel: Channel<BaseEvent>,
        mapChannel: Channel<MapEvent>
    ) {
        sendUpdate(BlockUpdater(restaurant, false), updateChannel)
        sendUpdate(ReportedUpdater("", restaurant.id), updateChannel)
        try {
            val response = FourSquareRepository.reportBusinessAsClosed(restaurant.id)
            val reportId = response.body()?.proposed_edits?.getOrNull(0)?.reportId
            if (reportId != null) {
                sendUpdate(ReportedUpdater(reportId, restaurant.id), updateChannel)
            } else {
                sendUpdate(ReportFailedUpdater(restaurant.id), updateChannel)
            }
        } catch (throwable: Throwable) {
            sendUpdate(ReportFailedUpdater(restaurant.id), updateChannel)
        }
    }
}