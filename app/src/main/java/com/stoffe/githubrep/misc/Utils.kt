package com.stoffe.githubrep.misc

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object Utils {

    fun launchCustomTab(context: Context, url: String) {
        val uri = Uri.parse(url)

        CustomTabsIntent.Builder()
            .build()
            .launchUrl(context, uri)
    }
}