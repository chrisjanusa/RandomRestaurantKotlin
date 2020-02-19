package com.chrisjanusa.randomizer.deeplinks

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import java.net.URLEncoder

object DeeplinkHelper {

    fun openDeeplink(url: String, context: Context) {
        val uris = Uri.parse(url)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        context.startActivity(intents)
    }

    fun encodeUrl(string: String) : String {
        return URLEncoder.encode(string, "UTF-8")
    }
}