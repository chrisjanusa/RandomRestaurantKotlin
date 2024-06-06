package com.chrisjanusa.randomizer.foursquare.updaters

import com.chrisjanusa.base.interfaces.BaseUpdater
import com.chrisjanusa.base.models.RandomizerState
import kotlinx.coroutines.Job

class CacheJobUpdater(private val cacheUpdateJob: Job?) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(lastCacheUpdateJob = cacheUpdateJob)
    }

}