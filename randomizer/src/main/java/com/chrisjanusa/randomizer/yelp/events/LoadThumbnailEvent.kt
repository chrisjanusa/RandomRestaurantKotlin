package com.chrisjanusa.randomizer.yelp.events

import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chrisjanusa.randomizer.R
import com.chrisjanusa.base.interfaces.BaseEvent

class LoadThumbnailEvent(private val image_url: String?) : BaseEvent {
    override fun handleEvent(fragment: Fragment) {
        Log.wtf("glide_bug", "isAdded Event: ${fragment.isAdded}")
        fragment.view?.findViewById<ImageView>(R.id.thumbnail)?.let {
            Glide.with(fragment)
                .load(image_url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(it)
        }
    }
}