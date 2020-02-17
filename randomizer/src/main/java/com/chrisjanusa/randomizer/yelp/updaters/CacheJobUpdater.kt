package com.chrisjanusa.randomizer.yelp.updaters

import com.chrisjanusa.randomizer.base.interfaces.BaseUpdater
import com.chrisjanusa.randomizer.base.models.RandomizerState
import kotlinx.coroutines.Job

class CacheJobUpdater(private val cacheUpdateJob: Job?) : BaseUpdater {
    override fun performUpdate(prevState: RandomizerState): RandomizerState {
        return prevState.copy(lastCacheUpdateJob = cacheUpdateJob)
    }

}